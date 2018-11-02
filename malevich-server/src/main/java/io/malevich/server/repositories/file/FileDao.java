package io.malevich.server.repositories.file;


import io.malevich.server.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileDao extends JpaRepository<FileEntity, Long> {

}
