package io.malevich.server.dao.organization;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.OrganizationEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaOrganizationDao extends JpaDao<OrganizationEntity, Long> implements OrganizationDao {


    public JpaOrganizationDao() {
        super(OrganizationEntity.class);
    }


}