package io.malevich.server.repositories.counterpartytype;

import io.malevich.server.domain.CounterpartyTypeEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounterpartyTypeDao extends JpaRepository<CounterpartyTypeEntity, String> {

//    @Cacheable(value = "CounterpartyTypeDao.findAll")
    List<CounterpartyTypeEntity> findAll();
}
