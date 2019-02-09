package io.malevich.server.repositories.kyclevel;

import io.malevich.server.domain.KycLevelEntity;
import io.malevich.server.domain.enums.KycLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KycLevelDao extends JpaRepository<KycLevelEntity, KycLevel> {

    List<KycLevelEntity> findAll();

}
