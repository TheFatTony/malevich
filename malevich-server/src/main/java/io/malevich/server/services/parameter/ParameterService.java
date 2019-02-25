package io.malevich.server.services.parameter;

import io.malevich.server.domain.ParameterEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParameterService {

    List<ParameterEntity> findAll();
}
