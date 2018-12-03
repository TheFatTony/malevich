package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "payment_method")
public class PaymentMethodEntity implements Entity {


    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
