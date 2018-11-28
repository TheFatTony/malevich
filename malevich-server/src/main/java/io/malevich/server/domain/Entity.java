package io.malevich.server.domain;

import org.springframework.data.domain.Persistable;

import javax.persistence.Transient;


public interface Entity extends Persistable {


    @Transient
    default boolean isNew() {
        return null == this.getId();
    }

}
