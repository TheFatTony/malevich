package io.malevich.server.domain;


import io.malevich.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "country")
public class CountryEntity implements Entity {

    @Getter
    @Setter
    @Id
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "name_ml")
    private Map<String, String> nameMl;

}
