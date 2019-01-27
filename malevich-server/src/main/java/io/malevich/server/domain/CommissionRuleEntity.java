package io.malevich.server.domain;


import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;


@javax.persistence.Entity
@Table(name = "commission_rule")
public class CommissionRuleEntity extends YAbstractPersistable<Long> {


    @Getter
    @Setter
    @Column(name = "name")
    private String name;


    @Getter
    @Setter
    @Column(name = "value")
    private BigDecimal value;

}
