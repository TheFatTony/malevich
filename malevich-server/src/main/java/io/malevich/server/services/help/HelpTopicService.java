package io.malevich.server.services.help;


import io.malevich.server.domain.HelpTopicEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HelpTopicService {

    HelpTopicEntity save(HelpTopicEntity entity);

    List<HelpTopicEntity> findAll();

    List<HelpTopicEntity> findByCategoryId(Long id);

    List<HelpTopicEntity> filter(String lang, String searchValue);
}
