package io.malevich.server.services.help;


import io.malevich.server.domain.HelpTopicEntity;
import io.malevich.server.repositories.help.HelpTopicDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class HelpTopicServiceImpl implements HelpTopicService {

    @Autowired
    private HelpTopicDao helpTopicDao;

    protected HelpTopicServiceImpl() {
    }

    @Override
    @Transactional
    public HelpTopicEntity save(HelpTopicEntity entity) {
        return this.helpTopicDao.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HelpTopicEntity> findAll() {
        return this.helpTopicDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HelpTopicEntity> findByCategoryId(Long id) {
        return this.helpTopicDao.findByCategoryId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HelpTopicEntity> filter(String lang, String searchValue) {
        return this.helpTopicDao.filter(lang, searchValue);
    }
}
