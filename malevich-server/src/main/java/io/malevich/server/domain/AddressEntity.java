package io.malevich.server.domain;


import com.yinyang.core.server.domain.YAbstractPersistable;
import io.malevich.server.aop.KycRequiredFor;
import io.malevich.server.domain.enums.KycLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@javax.persistence.Entity
@Table(name = "address")
public class AddressEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "street")
    @KycRequiredFor(levels = {KycLevel.T_TIER2, KycLevel.G_TIER1})
    private String street;

    @Getter
    @Setter
    @Column(name = "postal_code")
    @KycRequiredFor(levels = {KycLevel.T_TIER2, KycLevel.G_TIER1})
    private String postalCode;

    @Getter
    @Setter
    @Column(name = "state")
    @KycRequiredFor(levels = {KycLevel.T_TIER2, KycLevel.G_TIER1})
    private String state;

    @Getter
    @Setter
    @Column(name = "city")
    @KycRequiredFor(levels = {KycLevel.T_TIER2, KycLevel.G_TIER1})
    private String city;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @KycRequiredFor(levels = {KycLevel.T_TIER2, KycLevel.G_TIER1})
    private CountryEntity country;

}
