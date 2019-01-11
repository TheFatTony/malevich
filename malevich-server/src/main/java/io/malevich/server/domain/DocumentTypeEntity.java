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
@Table(name = "document_type")
public class DocumentTypeEntity extends YAbstractPersistable<Long> {


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
