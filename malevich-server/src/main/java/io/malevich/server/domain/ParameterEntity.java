package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "parameter")
public class ParameterEntity extends YAbstractPersistable<String> {


    @Getter
    @Setter
    @Column(name = "value")
    private String value;

}
