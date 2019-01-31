import {PaymentMethodTypeDto} from "./paymentMethodTypeDto";

export class PaymentMethodDto {
  id: number;
  type: PaymentMethodTypeDto;
  // payload: Map<string, string>;

  cardNumber: string;
  cardHolder: string;
  cardExpiration: string;
  cardCvv: number;

  btcAddress: string;
}
