package io.malevich.server.domain;


import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "country")
public class CountryEntity extends AbstractPersistable<String> {


    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "name_ml")
    @NotNull
    private Map<String, String> nameMl;

}
