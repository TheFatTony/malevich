package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "payment_method")
@Inheritance(strategy = InheritanceType.JOINED)
public class PaymentMethodEntity extends YAbstractPersistable<Long> {


    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private PaymentMethodTypeEntity type;


}
