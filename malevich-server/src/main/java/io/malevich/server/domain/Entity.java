package io.malevich.server.domain;

import org.springframework.data.domain.Persistable;

import javax.persistence.Transient;
import java.io.Serializable;


public interface Entity extends Persistable, Serializable {


    @Transient
    default boolean isNew() {
        return null == this.getId();
    }

}
