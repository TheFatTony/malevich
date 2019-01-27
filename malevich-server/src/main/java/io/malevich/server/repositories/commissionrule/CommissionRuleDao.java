package io.malevich.server.repositories.commissionrule;

import io.malevich.server.domain.CommissionRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRuleDao extends JpaRepository<CommissionRuleEntity, Long> {

    CommissionRuleEntity findByName(String name);
}
