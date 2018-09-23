package io.malevich.server.services.organization;


import io.malevich.server.dao.artwork.ArtworkDao;
import io.malevich.server.dao.organization.OrganizationDao;
import io.malevich.server.entity.ArtworkEntity;
import io.malevich.server.entity.OrganizationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class OrganizationServiceImpl implements OrganizationService {


    @Autowired
    private OrganizationDao organizationDao;

    protected OrganizationServiceImpl() {
    }

    @Override
    @Transactional
    public List<OrganizationEntity> findAll() {
        return this.organizationDao.findAll();
    }

    @Override
    @Transactional
    public OrganizationEntity find(Long id) {
        return this.organizationDao.find(id);
    }

}
