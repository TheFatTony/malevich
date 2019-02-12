package io.malevich.server.services.kyc;

import io.malevich.server.aop.KycRequiredFor;
import io.malevich.server.domain.*;
import io.malevich.server.domain.enums.KycLevel;
import io.malevich.server.exceptions.KycSecurityException;
import io.malevich.server.repositories.kyclevel.KycLevelDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KycLevelServiceImpl implements KycLevelService {
    private final Map<String, KycLevelEntity> values;

    private KycLevelDao kycLevelDao;

    @Autowired
    public KycLevelServiceImpl(KycLevelDao kycLevelDao) {
        this.kycLevelDao = kycLevelDao;
        values = kycLevelDao.findAll().stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
    }

    @Override
    @Transactional(readOnly = true)
    public List<KycLevelEntity> findAll() {
        return this.kycLevelDao.findAll();
    }

    @Override
    public List<KycLevelEntity> getDetailing(String levelName) {
        KycLevelEntity entity = values.get(levelName);
        return values.values()
                .stream()
                .filter(l -> contains(entity, l))
                .collect(Collectors.toList());
    }

    private KycLevelEntity getByEnum(KycLevel enumLevel) {
        return values.get(enumLevel.name());
    }

    private KycLevel getEnum(KycLevelEntity entity) {
        return KycLevel.valueOf(entity.getId());
    }

    private KycLevelEntity getTraderTier0() {
        return values.get("T_TIER0");
    }

    private KycLevelEntity getTraderTier1() {
        return values.get("T_TIER1");
    }

    private KycLevelEntity getGalleryTier0() {
        return values.get("G_TIER0");
    }

    private KycLevelEntity getGalleryTier1() {
        return values.get("G_TIER1");
    }


    private boolean contains(KycLevelEntity requiredLevel, KycLevelEntity testLevel) {
        if (requiredLevel == null)
            return true;

        if (testLevel == null)
            return false;

        return (requiredLevel.getValue() & testLevel.getValue()) == testLevel.getValue();
    }

    private KycLevelEntity getBaseTier0(KycLevelEntity kycLevelEntity) {
        if (contains(kycLevelEntity, getTraderTier0()))
            return getTraderTier0();

        if (contains(kycLevelEntity, getGalleryTier0()))
            return getGalleryTier0();

        return null;
    }

    private KycSecurityException getCheckLevelException(KycLevelEntity currentLevel, KycLevel[] requiredLevels) {
        if (requiredLevels == null || requiredLevels.length == 0)
            return null;

        if (currentLevel == null)
            return new KycSecurityException(requiredLevels[0], null, new String[]{});

        KycLevelEntity baseLevel = getBaseTier0(currentLevel);

        for (KycLevel requiredLevel : requiredLevels) {
            KycLevelEntity requiredLevelEntity = getByEnum(requiredLevel);

            if (!contains(requiredLevelEntity, baseLevel))
                continue;

            if (!contains(currentLevel, requiredLevelEntity))
                return new KycSecurityException(requiredLevel, getEnum(currentLevel), new String[]{});
        }

        return null;
    }

    @Override
    public boolean checkLevel(String testLevel, List<String> requiredLevels) {
        KycLevelEntity testLevelEntity = getByEnum(KycLevel.valueOf(testLevel));
        return getCheckLevelException(testLevelEntity, requiredLevels.stream().map(i -> KycLevel.valueOf(i)).toArray(KycLevel[]::new)) == null;
    }

    @Override
    public void checkLevelOrException(ParticipantEntity participantEntity, KycLevel[] requiredLevels) {
        KycLevelEntity currentLevel = participantEntity.getKycLevel();
        KycSecurityException exception = getCheckLevelException(currentLevel, requiredLevels);
        if (exception != null)
            throw exception;
    }

    @Override
    public KycLevelEntity getLevel(ParticipantEntity participantEntity) {
        if (participantEntity instanceof GalleryEntity) {
            return isGalleryTier1((GalleryEntity) participantEntity)
                    ? getGalleryTier1()
                    : getGalleryTier0();
        } else {
            KycLevelEntity result = getTraderTier0();

            if (!isTraderTier1(participantEntity))
                return result;

            result = getTraderTier1();

            return result;
        }
    }

    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    private boolean hasLevel(Object obj, KycLevelEntity testLevel) {

        for (Field field : getAllFields(new LinkedList<>(), obj.getClass())) {
            KycRequiredFor kycRequiredFor = field.getAnnotation(KycRequiredFor.class);

            if (kycRequiredFor == null)
                continue;

            for (KycLevel level : kycRequiredFor.levels()) {
                KycLevelEntity levelEntity = getByEnum(level);

                if (!testLevel.equals(levelEntity))
                    continue;

                try {
                    // todo crap
                    field.setAccessible(true);
                    if (field.get(obj) == null)
                        return false;
                } catch (IllegalAccessException e) {
                    log.error("IllegalAccessException", e);
//                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isGalleryTier1(GalleryEntity galleryEntity) {
        if (!hasLevel(galleryEntity, getGalleryTier1()))
            return false;

        OrganizationEntity organizationEntity = galleryEntity.getOrganization();

        if (!hasLevel(organizationEntity, getGalleryTier1()))
            return false;

        return true;
    }

    private boolean isTraderTier1(ParticipantEntity participantEntity) {
        if (!hasLevel(participantEntity, getTraderTier1()))
            return false;

        if (participantEntity instanceof TraderPersonEntity) {
            TraderPersonEntity traderPersonEntity = (TraderPersonEntity) participantEntity;
            PersonEntity personEntity = traderPersonEntity.getPerson();

            if (!hasLevel(personEntity, getTraderTier1()))
                return false;

        } else if (participantEntity instanceof TraderOrganizationEntity) {
            TraderOrganizationEntity traderOrganizationEntity =
                    (TraderOrganizationEntity) participantEntity;

            OrganizationEntity organizationEntity = traderOrganizationEntity.getOrganization();

            if (!hasLevel(organizationEntity, getTraderTier1()))
                return false;
        }
        return true;
    }
}
