package io.malevich.server.services.category;


import io.malevich.server.dao.category.CategoryDao;
import io.malevich.server.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryDao categoryDao;

    protected CategoryServiceImpl() {
    }

    @Override
    @Transactional
    public List<CategoryEntity> findAll() {
        return this.categoryDao.findAll();
    }

    @Override
    @Transactional
    public CategoryEntity find(Long id) {
        return this.categoryDao.find(id);
    }

}
