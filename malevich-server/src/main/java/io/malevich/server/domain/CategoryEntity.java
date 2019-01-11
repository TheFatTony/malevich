package io.malevich.server.domain;


import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "category")
public class CategoryEntity extends AbstractPersistable<Long> {



    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "category_name_ml")
    @NotNull
    private Map<String, String> categoryNameMl;


}
