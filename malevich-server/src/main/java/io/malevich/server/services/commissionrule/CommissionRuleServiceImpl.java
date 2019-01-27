package io.malevich.server.services.commissionrule;

import io.malevich.server.domain.CommissionRuleEntity;
import io.malevich.server.repositories.commissionrule.CommissionRuleDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CommissionRuleServiceImpl implements CommissionRuleService {

    @Autowired
    CommissionRuleDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleEntity> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CommissionRuleEntity findByName(String name) {
        return dao.findByName(name);
    }
}
