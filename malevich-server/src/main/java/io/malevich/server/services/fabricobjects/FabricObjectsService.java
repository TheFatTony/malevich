package io.malevich.server.services.fabricobjects;

import io.malevich.server.domain.FabricObjectsEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface FabricObjectsService {


    FabricObjectsEntity save(FabricObjectsEntity fabricObjectsEntity);

    List<FabricObjectsEntity> findAll();

}
