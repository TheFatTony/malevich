package io.malevich.server.repositories.participanttype;

import io.malevich.server.domain.ParticipantTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantTypeDao extends JpaRepository<ParticipantTypeEntity, String> {

    List<ParticipantTypeEntity> findAll();
}
