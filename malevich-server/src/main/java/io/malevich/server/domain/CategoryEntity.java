package io.malevich.server.domain;


import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;
import com.yinyang.core.server.domain.Entity;

@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "category")
public class CategoryEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
