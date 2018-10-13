package io.malevich.server.dao.lobstorage;


import io.malevich.server.entity.LobStorageEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LobStorageDao extends JpaRepository<LobStorageEntity, Long> {


    @Query("select lse from LobStorageEntity lse join fetch lse.file")
    List<LobStorageEntity> findAll();

    @Cacheable(value = "LobStorageDao.findByFileId")
    @Query("select lse from LobStorageEntity lse join fetch lse.file where lse.file.id = :fileId")
    LobStorageEntity findByFileId(@Param("fileId") Long fileId);
}