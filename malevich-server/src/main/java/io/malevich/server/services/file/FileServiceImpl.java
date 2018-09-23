package io.malevich.server.services.file;


import io.malevich.server.dao.file.FileDao;
import io.malevich.server.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    protected FileServiceImpl() {
    }

    @Override
    @Transactional
    public FileEntity find(Long id) {
        return this.fileDao.find(id);
    }

    @Override
    public List<FileEntity> findAll() {
        return fileDao.findAll();
    }
}
