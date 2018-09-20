package io.malevich.server.dao.category;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaCategoryDao extends JpaDao<CategoryEntity, Long> implements CategoryDao {


    public JpaCategoryDao() {
        super(CategoryEntity.class);
    }


}
