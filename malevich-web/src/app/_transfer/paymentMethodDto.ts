import {PaymentMethodTypeDto} from "./paymentMethodTypeDto";
import {CountryDto} from "./countryDto";

export class PaymentMethodDto {
  id: number;
  type: PaymentMethodTypeDto;
  // payload: Map<string, string>;

  cardNumber: string;
  cardHolder: string;
  cardExpiration: string;
  cardCvv: number;

  btcAddress: string;

  iban: string;
  beneficiaryName: string;
  beneficiaryCountry: CountryDto;
  beneficiaryAddress: string;
  bic: string;
  bankName: string;
  bankCountry: CountryDto;
  bankAddress: string;
}
