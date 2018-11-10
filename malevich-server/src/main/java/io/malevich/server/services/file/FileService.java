package io.malevich.server.services.file;

import io.malevich.server.domain.FileEntity;
import org.apache.tomcat.jni.File;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface FileService {

    FileEntity find(Long id);

    List<FileEntity> findAll();

    FileEntity save(FileEntity fileEntity);
}
