package io.malevich.server.services.lobstorage;


import io.malevich.server.repositories.lobstorage.LobStorageDao;
import io.malevich.server.domain.LobStorageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class LobStorageServiceImpl implements LobStorageService {


    @Autowired
    private LobStorageDao lobStorageDao;

    protected LobStorageServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public LobStorageEntity findByFileId(Long fileId) {
        return lobStorageDao.findByFileId(fileId);
    }
}
