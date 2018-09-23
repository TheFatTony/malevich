package io.malevich.server.dao.file;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaFileDao extends JpaDao<FileEntity, Long> implements FileDao {

    public JpaFileDao() {
        super(FileEntity.class);
    }

}
