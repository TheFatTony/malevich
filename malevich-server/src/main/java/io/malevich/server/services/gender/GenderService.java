package io.malevich.server.services.gender;


import io.malevich.server.domain.GenderEntity;
import io.malevich.server.domain.ParameterEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenderService {

    List<GenderEntity> findAll();
}

