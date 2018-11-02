package io.malevich.server.services.organization;


import io.malevich.server.domain.OrganizationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationService {

    List<OrganizationEntity> findAll();

    OrganizationEntity find(Long id);
}
