package io.malevich.server.dao.counterpartytype;

import io.malevich.server.entity.CounterpartyTypeEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounterpartyTypeDao extends JpaRepository<CounterpartyTypeEntity, Long> {

    @Cacheable(value = "custom")
    List<CounterpartyTypeEntity> findAll();
}
