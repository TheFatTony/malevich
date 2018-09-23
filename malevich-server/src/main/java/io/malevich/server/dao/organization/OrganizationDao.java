package io.malevich.server.dao.organization;

import io.malevich.server.dao.Dao;
import io.malevich.server.entity.OrganizationEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrganizationDao extends Dao<OrganizationEntity, Long> {

    List<OrganizationEntity> findAll();

    OrganizationEntity find(Long id);
}