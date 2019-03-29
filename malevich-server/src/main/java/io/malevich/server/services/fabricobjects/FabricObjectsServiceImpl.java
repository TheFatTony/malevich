package io.malevich.server.services.fabricobjects;

import io.malevich.server.domain.FabricObjectsEntity;
import io.malevich.server.repositories.fabricobjects.FabricObjectsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class FabricObjectsServiceImpl implements FabricObjectsService {

    @Autowired
    private FabricObjectsDao fabricObjectsDao;


    @Override
    @Transactional
    public FabricObjectsEntity save(FabricObjectsEntity fabricObjectsEntity) {
        return fabricObjectsDao.save(fabricObjectsEntity);
    }


    @Override
    @Transactional(readOnly = true)
    public List<FabricObjectsEntity> findAll() {
        return fabricObjectsDao.findAll();
    }


}
