package io.malevich.server.services.kyc;

import io.malevich.server.domain.*;
import io.malevich.server.domain.enums.KycLevel;
import io.malevich.server.exceptions.KycSecurityException;
import io.malevich.server.repositories.kyclevel.KycLevelDao;
import io.malevich.server.services.document.DocumentService;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KycLevelServiceImpl implements KycLevelService {
    private final Map<String, KycLevelEntity> values;

    private KycLevelDao kycLevelDao;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ParticipantService participantService;

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
    public Map<String, Boolean> getDetailing(String levelName) {
        KycLevelEntity entity = values.get(levelName);
        return values.values()
                .stream()
                .collect(Collectors.toMap(i -> i.getId(), i -> contains(entity, i)));
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

    private KycLevelEntity getTraderTier2() {
        return values.get("T_TIER2");
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
        KycLevelEntity currentLevel = participantEntity != null ? participantEntity.getKycLevel() : null;
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

            if (!isTraderTier2(participantEntity))
                return result;

            result = getTraderTier2();

            return result;
        }
    }

    @Override
    @Transactional
    public void updateLevel(ParticipantEntity participantEntity) {
        KycLevelEntity newLevel = getLevel(participantEntity);

        if (newLevel.equals(participantEntity.getKycLevel()))
            return;

        participantEntity.setKycLevel(newLevel);
        participantService.saveAsIs(participantEntity);
    }

    private boolean check(Object obj) {
        return obj != null;
    }

    private boolean checkDocumentsOfType(ParticipantEntity participantEntity, String[] types) {
        List<DocumentEntity> documents =
                documentService.findByParticipantId(participantEntity.getId());

        HashSet<String> docTypes = documents
                .stream()
                .map(d -> d.getDocumentType().getId())
                .collect(Collectors.toCollection(HashSet::new));

        return Arrays.stream(types).allMatch(i -> docTypes.contains(i));
    }

    private boolean isGalleryTier1(GalleryEntity galleryEntity) {
        if (!check(galleryEntity.getCountry()))
            return false;

        if (!check(galleryEntity.getPhoneNumber()))
            return false;

        if (!check(galleryEntity.getTitleMl()))
            return false;

        if (!check(galleryEntity.getOrganization()))
            return false;

        OrganizationEntity organizationEntity = galleryEntity.getOrganization();

        if (!check(organizationEntity.getLegalNameMl()))
            return false;

        List<AddressEntity> addresses = galleryEntity.getAddresses();
        if (addresses == null || addresses.size() == 0)
            return false;

        AddressEntity address = addresses.get(0);

        if (!check(address))
            return false;

        if (!check(address.getStreet()))
            return false;

        if (!check(address.getPostalCode()))
            return false;

        if (!check(address.getState()))
            return false;

        if (!check(address.getCity()))
            return false;

        if (!check(address.getCountry()))
            return false;

        if (!checkDocumentsOfType(galleryEntity, new String[]{"GAL_LIC", "CERT_INC"}))
            return false;

        return true;
    }

    private boolean isTraderTier1(ParticipantEntity participantEntity) {
        if (!check(participantEntity.getCountry()))
            return false;

        if (!check(participantEntity.getPhoneNumber()))
            return false;

        if (participantEntity instanceof TraderPersonEntity) {
            TraderPersonEntity traderPersonEntity = (TraderPersonEntity) participantEntity;
            PersonEntity personEntity = traderPersonEntity.getPerson();

            if (!check(personEntity))
                return false;

            if (!check(personEntity.getFirstName()))
                return false;

            if (!check(personEntity.getLastName()))
                return false;

            if (!check(personEntity.getGender()))
                return false;

            if (!check(personEntity.getDateOfBirth()))
                return false;

        } else if (participantEntity instanceof TraderOrganizationEntity) {
            TraderOrganizationEntity traderOrganizationEntity =
                    (TraderOrganizationEntity) participantEntity;

            OrganizationEntity organizationEntity = traderOrganizationEntity.getOrganization();

            if (!check(organizationEntity))
                return false;

            if (!check(organizationEntity.getLegalNameMl()))
                return false;
        }

        return true;
    }

    private boolean isTraderTier2(ParticipantEntity participantEntity) {
        List<AddressEntity> addresses = participantEntity.getAddresses();
        if (addresses == null || addresses.size() == 0)
            return false;

        AddressEntity address = addresses.get(0);

        if (!check(address))
            return false;

        if (!check(address.getStreet()))
            return false;

        if (!check(address.getPostalCode()))
            return false;

        if (!check(address.getState()))
            return false;

        if (!check(address.getCity()))
            return false;

        if (!check(address.getCountry()))
            return false;

        if (participantEntity instanceof TraderPersonEntity) {
            if (!checkDocumentsOfType(participantEntity, new String[]{"PASSPORT", "UTIL_BILL"}))
                return false;
        } else if (participantEntity instanceof TraderOrganizationEntity) {
            if (!checkDocumentsOfType(participantEntity, new String[]{"CERT_INC"}))
                return false;
        }

        return true;
    }
}
