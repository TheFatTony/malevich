package io.malevich.server.services.category;


import io.malevich.server.entity.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<CategoryEntity> findAll();

    CategoryEntity find(Long id);
}
