package io.malevich.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "payment_method_account")
public class PaymentMethodAccountEntity extends PaymentMethodEntity {


    @Getter
    @Setter
    @Column(name = "iban")
    @Pattern(regexp = "[0-9A-Z]+")
    private String iban;

    @Getter
    @Setter
    @Column(name = "beneficiary_name")
    @NotBlank
    private String beneficiaryName;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "beneficiary_country_id")
    @NotNull
    private CountryEntity beneficiaryCountry;

    @Getter
    @Setter
    @Column(name = "beneficiary_address")
    @NotBlank
    private String beneficiaryAddress;

    @Getter
    @Setter
    @Column(name = "bic")
    @Pattern(regexp = "[0-9A-Z]+")
    private String bic;

    @Getter
    @Setter
    @Column(name = "bank_name")
    private String bankName;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "bank_country_id")
    @NotNull
    private CountryEntity bankCountry;

    @Getter
    @Setter
    @Column(name = "bank_address")
    @NotBlank
    private String bankAddress;

    @Getter
    @Setter
    @Column(name = "revolut_counterparty_id")
    private String revolutCounterpartyId;

    @Getter
    @Setter
    @Column(name = "revolut_account_id")
    private String revolutAccountId;

}
