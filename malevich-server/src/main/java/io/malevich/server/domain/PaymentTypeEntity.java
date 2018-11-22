package io.malevich.server.domain;

import io.malevich.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "payment_type")
public class PaymentTypeEntity implements Entity {

    @Getter
    @Setter
    @Id
    @Column(name = "id")
    private String id;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "name_ml")
    private Map<String, String> nameMl;

}
