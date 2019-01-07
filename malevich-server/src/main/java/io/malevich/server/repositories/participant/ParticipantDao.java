package io.malevich.server.repositories.participant;

import io.malevich.server.domain.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantDao extends JpaRepository<ParticipantEntity, Long> {

    List<ParticipantEntity> findAll();

    Optional<ParticipantEntity> findByUsers_Name(String name);
}
