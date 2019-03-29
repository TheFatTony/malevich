package io.malevich.server.repositories.fabricobjects;

import io.malevich.server.domain.FabricObjectsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FabricObjectsDao extends JpaRepository<FabricObjectsEntity, Long> {

    List<FabricObjectsEntity> findByTypeId(String typeId);

}
