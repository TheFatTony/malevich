package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "payment_method")
public class PaymentMethodEntity extends AbstractPersistable<Long> {


    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.MERGE)
    private PaymentMethodTypeEntity type;

    @Getter
    @Setter
    @Column(name = "payload")
    @Convert(converter = JpaConverterJson.class)
    private Object payload;

}
