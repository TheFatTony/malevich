package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodSuperDto extends PaymentMethodDto {
    private String cardNumber;
    private String cardHolder;
    private String cardExpiration;
    private String cardCvv;

    private String btcAddress;

    private String iban;
    private String beneficiaryName;
    private CountryDto beneficiaryCountry;
    private String beneficiaryAddress;
    private String bic;
    private String bankName;
    private CountryDto bankCountry;
    private String bankAddress;

}
