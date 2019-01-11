package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@javax.persistence.Entity
@Table(name = "delayed_change")
public class DelayedChangeEntity extends AbstractPersistable<Long> {



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
    @NotNull
    private Long referenceId;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @NotNull
    private UserEntity user;

}
