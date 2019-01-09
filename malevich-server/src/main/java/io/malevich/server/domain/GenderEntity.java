package io.malevich.server.domain;


import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Map;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "gender")
public class GenderEntity implements Entity {

    @Getter
    @Setter
    @Id
    private String id;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "name_ml")
    @NotNull
    private Map<String, String> nameMl;

}
