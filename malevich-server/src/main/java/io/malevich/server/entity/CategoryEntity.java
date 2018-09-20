package io.malevich.server.entity;


import io.malevich.server.entity.utils.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;


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
    @Column(name = "category_name")
    private String categoryName;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "category_name_ml")
    private Map<String, String> categoryNameMl;


}
