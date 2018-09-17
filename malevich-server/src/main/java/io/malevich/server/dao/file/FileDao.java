package io.malevich.server.dao.file;


import io.malevich.server.dao.Dao;
import io.malevich.server.entity.FileEntity;
import org.springframework.stereotype.Component;


@Component
public interface FileDao extends Dao<FileEntity, Long> {

    FileEntity find(Long id);
}
