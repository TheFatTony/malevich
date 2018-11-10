package io.malevich.server.services.lobstorage;

import io.malevich.server.domain.LobStorageEntity;
import org.springframework.stereotype.Service;


@Service
public interface LobStorageService {

    LobStorageEntity findByFileId(Long fileId);

    LobStorageEntity save(LobStorageEntity lobStorageEntity);
}
