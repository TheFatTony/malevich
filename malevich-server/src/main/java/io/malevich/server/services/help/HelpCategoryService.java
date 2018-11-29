package io.malevich.server.services.help;


import io.malevich.server.domain.HelpCategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HelpCategoryService {

    HelpCategoryEntity save(HelpCategoryEntity entity);

    List<HelpCategoryEntity> findAll();
}
