package io.malevich.server.domain;


import com.yinyang.core.server.domain.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.validation.constraints.NotNull;


import javax.persistence.*;


@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "address")
public class AddressEntity implements Entity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
