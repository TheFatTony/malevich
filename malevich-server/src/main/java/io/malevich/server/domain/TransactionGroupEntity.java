package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "transaction_group")
public class TransactionGroupEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

}
