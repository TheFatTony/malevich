package io.malevich.server.dao.lobstorage;


import io.malevich.server.dao.Dao;
import io.malevich.server.entity.LobStorageEntity;
import org.springframework.stereotype.Component;


@Component
public interface LobStorageDao extends Dao<LobStorageEntity, Long> {

    LobStorageEntity find(Long id);

    LobStorageEntity findByFileId(Long fileId);
}
