package io.malevich.server.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@javax.persistence.Entity
@Table(name = "address")
public class AddressEntity extends AbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "street")
    @NotNull
    private String street;

    @Getter
    @Setter
    @Column(name = "postal_code")
    @NotNull
    private String postalCode;

    @Getter
    @Setter
    @Column(name = "state")
    @NotNull
    private String state;

    @Getter
    @Setter
    @Column(name = "city")
    @NotNull
    private String city;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @NotNull
    private CountryEntity country;

}
