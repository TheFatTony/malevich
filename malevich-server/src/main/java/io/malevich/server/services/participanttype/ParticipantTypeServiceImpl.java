package io.malevich.server.services.participanttype;

import io.malevich.server.domain.ParticipantTypeEntity;
import io.malevich.server.repositories.participanttype.ParticipantTypeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParticipantTypeServiceImpl implements ParticipantTypeService {

    private final Map<String, ParticipantTypeEntity> values;

    private ParticipantTypeDao participantTypeDao;

    @Autowired
    public ParticipantTypeServiceImpl(ParticipantTypeDao participantTypeDao) {
        this.participantTypeDao = participantTypeDao;

        values = participantTypeDao.findAll().stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
    }


    @Override
    @Transactional(readOnly = true)
    public List<ParticipantTypeEntity> findAll() {
        return this.participantTypeDao.findAll();
    }

    @Override
    public ParticipantTypeEntity getTraderPersonType(){
        return values.get("TP");
    }

    @Override
    public ParticipantTypeEntity getTraderOrganizationType(){
        return values.get("TO");
    }

    @Override
    public ParticipantTypeEntity getGalleryType(){
        return values.get("G");
    }
}
