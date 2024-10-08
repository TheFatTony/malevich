package io.malevich.server.repositories.category;

import io.malevich.server.domain.CategoryEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends JpaRepository<CategoryEntity, Long> {


    @Cacheable(value = "CategoryDao.findAll")
    List<CategoryEntity> findAll();
}
