package io.malevich.server.domain;

import io.malevich.server.core.jpa.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "order_status")
public class OrderStatusEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "name_ml")
    private String nameMl;

}
