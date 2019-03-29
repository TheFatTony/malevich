package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.UserEntity;
import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@javax.persistence.Entity
@Table(name = "fabric_objects")
public class FabricObjectsEntity extends YAbstractPersistable<Long> {


    @Getter
    @Setter
    @Column(name = "type_id")
    @NotNull
    private String typeId;

    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "payload")
    @NotNull
    private Object payload;

    @Getter
    @Setter
    @Column(name = "reference_id")
    private String referenceId;

}
