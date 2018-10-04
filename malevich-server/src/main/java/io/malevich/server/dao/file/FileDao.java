package io.malevich.server.dao.file;


import io.malevich.server.entity.FileEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileDao extends JpaRepository<FileEntity, Long> {

}
