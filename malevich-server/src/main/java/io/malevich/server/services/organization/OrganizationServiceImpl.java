package io.malevich.server.services.organization;


import io.malevich.server.dao.organization.OrganizationDao;
import io.malevich.server.entity.OrganizationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrganizationServiceImpl implements OrganizationService {


    @Autowired
    private OrganizationDao organizationDao;

    protected OrganizationServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationEntity> findAll() {
        return this.organizationDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationEntity find(Long id) {
        return this.organizationDao.find(id);
    }

}
