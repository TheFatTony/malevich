package io.malevich.server.repositories.organization;

import io.malevich.server.domain.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDao extends JpaRepository<OrganizationEntity, Long> {

}