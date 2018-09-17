package io.malevich.server.services.lobstorage;


import io.malevich.server.dao.lobstorage.LobStorageDao;
import io.malevich.server.entity.LobStorageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LobStorageServiceImpl implements LobStorageService {


    @Autowired
    private LobStorageDao lobStorageDao;

    protected LobStorageServiceImpl() {
    }

    @Override
    public LobStorageEntity findByFileId(Long fileId) {
        return lobStorageDao.findByFileId(fileId);
    }
}
