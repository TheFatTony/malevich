package io.malevich.server.services.kyc;

import io.malevich.server.domain.KycLevelEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PersonEntity;
import io.malevich.server.domain.TraderPersonEntity;
import io.malevich.server.domain.enums.KycLevel;
import io.malevich.server.repositories.kyclevel.KycLevelDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private KycLevelEntity getTraderTier0() {
        return values.get("T_TIER0");
    }

    private KycLevelEntity getTraderTier1() {
        return values.get("T_TIER1");
    }

    @Override
    public boolean contains(KycLevelEntity requiredLevel, KycLevelEntity testLevel) {
        if (requiredLevel == null)
            return true;

        if (testLevel == null)
            return false;

        return (requiredLevel.getValue() & testLevel.getValue()) == testLevel.getValue();
    }

    @Override
    public KycLevelEntity getLevel(ParticipantEntity participantEntity) {
        return getTraderTier0();
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || "".equals(value);
    }



    private boolean isTier1(ParticipantEntity participantEntity) {
        if (participantEntity == null)
            return false;

        if(participantEntity.getCountry() == null)
            return false;

        PersonEntity personEntity = participantEntity.getPerson();

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

        return true;
    }
}
