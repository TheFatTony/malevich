package io.malevich.server.services.commissionrule;

import io.malevich.server.domain.CommissionRuleEntity;
import io.malevich.server.fabric.services.commissionrule.CommissionRuleAssetService;
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
    private CommissionRuleDao commissionRuleDao;

    @Autowired
    private CommissionRuleAssetService commissionRuleAssetService;

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleEntity> findAll() {
        return commissionRuleDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CommissionRuleEntity findByName(String name) {
        return commissionRuleDao.findByName(name);
    }

    @Override
    @Transactional
    public CommissionRuleEntity save(CommissionRuleEntity entity) {
        CommissionRuleEntity commissionRuleEntity = commissionRuleDao.save(entity);

        commissionRuleAssetService.create(commissionRuleEntity);

        return commissionRuleEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public CommissionRuleEntity find(Long id) {
        return this.commissionRuleDao.findById(id).get();
    }


}
