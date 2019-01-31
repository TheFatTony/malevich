package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@Entity
@Table(name = "payment_method_account")
public class PaymentMethodAccountEntity extends PaymentMethodEntity {


    @Getter
    @Setter
    @Column(name = "iban")
    private String iban;

    @Getter
    @Setter
    @Column(name = "beneficiary_name")
    private String beneficiaryName;

    @Getter
    @Setter
    @Column(name = "beneficiary_country")
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.DETACH)
    private CountryEntity beneficiaryCountry;

    @Getter
    @Setter
    @Column(name = "beneficiary_address")
    private String beneficiaryAddress;

    @Getter
    @Setter
    @Column(name = "bic")
    private String bic;

    @Getter
    @Setter
    @Column(name = "bank_name")
    private String bankName;

    @Getter
    @Setter
    @Column(name = "bank_country")
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.DETACH)
    private CountryEntity bankCountry;

    @Getter
    @Setter
    @Column(name = "bank_address")
    private String bankAddress;

}
