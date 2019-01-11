package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "organization")
public class OrganizationEntity extends YAbstractPersistable<Long> {



    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "legal_name_ml")
    @NotNull
    private Map<String, String> legalNameMl;


}
