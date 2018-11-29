package io.malevich.server.services.help;


import io.malevich.server.domain.HelpCategoryEntity;
import io.malevich.server.repositories.help.HelpCategoryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class HelpCategoryServiceImpl implements HelpCategoryService {

    @Autowired
    private HelpCategoryDao helpCategoryDao;

    protected HelpCategoryServiceImpl() {
    }

    @Override
    @Transactional
    public HelpCategoryEntity save(HelpCategoryEntity entity) {
        return this.helpCategoryDao.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HelpCategoryEntity> findAll() {
        return this.helpCategoryDao.findAll();
    }
}
