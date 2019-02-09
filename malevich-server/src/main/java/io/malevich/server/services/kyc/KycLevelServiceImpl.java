package io.malevich.server.services.kyc;

import io.malevich.server.domain.*;
import io.malevich.server.domain.enums.KycLevel;
import io.malevich.server.exceptions.KycSecurityException;
import io.malevich.server.fabric.model.GalleryParticipant;
import io.malevich.server.repositories.kyclevel.KycLevelDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
    public KycLevelEntity getByEnum(KycLevel enumLevel) {
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

    @Override
    public void checkLevel(ParticipantEntity participantEntity, KycLevel[] requiredLevels) {
        if (requiredLevels == null || requiredLevels.length == 0)
            return;

        KycLevelEntity currentLevel = participantEntity.getKycLevel();

        if (currentLevel == null)
            throw new KycSecurityException(requiredLevels[0], null, new String[]{});

        KycLevelEntity baseLevel = getBaseTier0(currentLevel);

        for (KycLevel requiredLevel : requiredLevels) {
            KycLevelEntity requiredLevelEntity = getByEnum(requiredLevel);

            if (!contains(requiredLevelEntity, baseLevel))
                continue;

            if (!contains(currentLevel, requiredLevelEntity))
                throw new KycSecurityException(requiredLevel, getEnum(currentLevel), new String[]{});
        }
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

    private static boolean isNullOrEmpty(String value) {
        return value == null || "".equals(value);
    }

    private boolean isGalleryTier1(GalleryEntity galleryEntity) {
        if (galleryEntity == null)
            return false;

        if (galleryEntity.getCountry() == null)
            return false;

        if (isNullOrEmpty(galleryEntity.getPhoneNumber()))
            return false;

        if (galleryEntity.getTitleMl() == null
                || isNullOrEmpty(galleryEntity.getTitleMl().get("en")))
            return false;

        OrganizationEntity organizationEntity = galleryEntity.getOrganization();

        if (organizationEntity.getLegalNameMl() == null
                || isNullOrEmpty(organizationEntity.getLegalNameMl().get("en")))
            return false;

        return true;
    }

    private boolean isTraderTier1(ParticipantEntity participantEntity) {
        if (participantEntity == null)
            return false;

        if (participantEntity.getCountry() == null)
            return false;

        if (isNullOrEmpty(participantEntity.getPhoneNumber()))
            return false;

        if (participantEntity instanceof TraderPersonEntity) {
            TraderPersonEntity traderPersonEntity = (TraderPersonEntity) participantEntity;
            PersonEntity personEntity = traderPersonEntity.getPerson();

            if (personEntity == null)
                return false;

            if (isNullOrEmpty(personEntity.getFirstName()))
                return false;

            if (isNullOrEmpty(personEntity.getLastName()))
                return false;

            if (personEntity.getDateOfBirth() == null)
                return false;

            if (isNullOrEmpty(participantEntity.getPhoneNumber()))
                return false;

            if (participantEntity.getCountry() == null)
                return false;
        } else if (participantEntity instanceof TraderOrganizationEntity) {
            TraderOrganizationEntity traderOrganizationEntity =
                    (TraderOrganizationEntity) participantEntity;

            OrganizationEntity organizationEntity = traderOrganizationEntity.getOrganization();

            if (organizationEntity.getLegalNameMl() == null
                    || isNullOrEmpty(organizationEntity.getLegalNameMl().get("en")))
                return false;
        }
        return true;
    }
}
