package io.malevich.server.services.lobstorage;

import io.malevich.server.entity.LobStorageEntity;
import org.springframework.stereotype.Service;


@Service
public interface LobStorageService {

    LobStorageEntity findByFileId(Long fileId);
}
