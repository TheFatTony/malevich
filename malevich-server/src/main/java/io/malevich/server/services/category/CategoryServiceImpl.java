package io.malevich.server.services.category;


import io.malevich.server.repositories.category.CategoryDao;
import io.malevich.server.domain.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryDao categoryDao;

    protected CategoryServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryEntity> findAll() {
        return this.categoryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryEntity find(Long id) {
        return this.categoryDao.findById(id).get();
    }

}
