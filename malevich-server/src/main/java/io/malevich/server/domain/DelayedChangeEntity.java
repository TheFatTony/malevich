package io.malevich.server.domain;

import io.malevich.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "delayed_change")
public class DelayedChangeEntity {


    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "type_id")
    private String typeId;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "payload")
    private Entity payload;

}
