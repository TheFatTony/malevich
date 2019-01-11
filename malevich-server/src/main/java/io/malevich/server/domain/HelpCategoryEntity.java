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
@Table(name = "help_category")
public class HelpCategoryEntity extends YAbstractPersistable<Long> {


    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "help_category_name_ml")
    @NotNull
    private Map<String, String> categoryNameMl;


}
