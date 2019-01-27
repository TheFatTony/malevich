package io.malevich.server.services.commissionrule;

import io.malevich.server.domain.CommissionRuleEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CommissionRuleService {

    List<CommissionRuleEntity> findAll();

    CommissionRuleEntity findByName(String name);

}
