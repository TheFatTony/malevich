package io.malevich.server.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "address")
public class AddressEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private TraderEntity trader;

    @Getter
    @Setter
    private String street;

    @Getter
    @Setter
    private String postalCode;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private CountryEntity country;

}
