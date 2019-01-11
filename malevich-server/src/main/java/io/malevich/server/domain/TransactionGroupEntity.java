package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;


@javax.persistence.Entity
@Table(name = "transaction_group")
public class TransactionGroupEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

}
