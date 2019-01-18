package io.malevich.server.repositories.country;

import io.malevich.server.domain.CountryEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryDao extends JpaRepository<CountryEntity, Long> {

//    @Cacheable(value = "CountryDao.findAll")
    List<CountryEntity> findAll();
}
