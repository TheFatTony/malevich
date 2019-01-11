package io.malevich.server.domain;


import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "help_topic")
public class HelpTopicEntity extends AbstractPersistable<Long> {



    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "body_ml")
    @NotNull
    private Map<String, String> bodyMl;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "topic_name_ml")
    @NotNull
    private Map<String, String> topicNameMl;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @NotNull
    private HelpCategoryEntity helpCategory;


}
