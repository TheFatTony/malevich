package io.malevich.server.domain;

import org.springframework.data.domain.Persistable;

import javax.persistence.Transient;
import java.io.Serializable;


public interface Entity<ID> extends Persistable<ID>, Serializable {


    @Transient
    default boolean isNew() {
        return null == this.getId();
    }

}
