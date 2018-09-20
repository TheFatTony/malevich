package io.malevich.server.dao.category;

import io.malevich.server.dao.Dao;
import io.malevich.server.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryDao extends Dao<CategoryEntity, Long> {

    List<CategoryEntity> findAll();

    CategoryEntity find(Long id);
}
