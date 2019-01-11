package io.malevich.server.domain;


import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "category")
public class CategoryEntity extends YAbstractPersistable<Long> {


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
