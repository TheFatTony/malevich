package io.malevich.server.domain;


import io.malevich.server.core.jpa.JpaConverterJson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Map;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "help_topic")
public class HelpTopicEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "body_ml")
    private Map<String, String> bodyMl;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "topic_name_ml")
    private Map<String, String> categoryNameMl;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private HelpCategoryEntity helpCategory;


}
