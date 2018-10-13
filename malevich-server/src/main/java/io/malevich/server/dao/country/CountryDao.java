package io.malevich.server.dao.country;

import io.malevich.server.entity.CountryEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryDao extends JpaRepository<CountryEntity, Long> {

    @Cacheable(value = "custom")
    List<CountryEntity> findAll();
}
