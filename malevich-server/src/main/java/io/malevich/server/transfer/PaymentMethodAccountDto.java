package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodAccountDto extends PaymentMethodDto{
    private String iban;
    private String beneficiaryName;
    private CountryDto beneficiaryCountry;
    private String beneficiaryAddress;
    private String bic;
    private String bankName;
    private CountryDto bankCountry;
    private String bankAddress;
}
