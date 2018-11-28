package io.malevich.server.repositories.delayedchange;

import io.malevich.server.domain.DelayedChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DelayedChangeDao extends JpaRepository<DelayedChangeEntity, Long> {

    List<DelayedChangeEntity> findByTypeId(String typeId);
}
