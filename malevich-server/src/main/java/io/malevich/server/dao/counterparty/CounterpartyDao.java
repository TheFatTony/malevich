package io.malevich.server.dao.counterparty;

import io.malevich.server.entity.CounterpartyEntity;
import io.malevich.server.entity.CounterpartyTypeEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounterpartyDao extends JpaRepository<CounterpartyEntity, Long> {

    List<CounterpartyEntity> findAll();

    CounterpartyEntity findCounterpartyEntitiesByGallery_Id(Long galleryId);
}
