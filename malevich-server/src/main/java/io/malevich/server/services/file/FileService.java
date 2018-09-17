package io.malevich.server.services.file;

import io.malevich.server.entity.FileEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface FileService {

    FileEntity find(Long id);

    List<FileEntity> findAll();
}
