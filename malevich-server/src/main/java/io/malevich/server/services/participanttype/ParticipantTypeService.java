package io.malevich.server.services.participanttype;

import io.malevich.server.domain.ParticipantTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ParticipantTypeService {

    List<ParticipantTypeEntity> findAll();

    ParticipantTypeEntity getTraderPersonType();

    ParticipantTypeEntity getTraderOrganizationType();

    ParticipantTypeEntity getGalleryType();
}
