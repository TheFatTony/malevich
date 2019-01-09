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
@Table(name = "document_type")
public class DocumentTypeEntity implements Entity {


    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "name_ml")
    @NotNull
    private Map<String, String> nameMl;


    @Getter
    @Setter
    @Column(name = "user_type")
    private String userType;

}
