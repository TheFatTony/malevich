package io.malevich.server.dao.category;

import io.malevich.server.entity.CategoryEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends JpaRepository<CategoryEntity, Long> {


    @Cacheable(value = "custom")
    List<CategoryEntity> findAll();
}
