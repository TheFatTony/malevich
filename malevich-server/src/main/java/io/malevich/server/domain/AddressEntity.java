package io.malevich.server.domain;


import com.yinyang.core.server.domain.YAbstractPersistable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@javax.persistence.Entity
@Table(name = "address")
public class AddressEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "street")
    private String street;

    @Getter
    @Setter
    @Column(name = "postal_code")
    private String postalCode;

    @Getter
    @Setter
    @Column(name = "state")
    private String state;

    @Getter
    @Setter
    @Column(name = "city")
    private String city;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    private CountryEntity country;

}
