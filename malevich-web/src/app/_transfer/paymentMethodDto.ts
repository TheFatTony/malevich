import {PaymentMethodTypeDto} from "./paymentMethodTypeDto";

export class PaymentMethodDto {
  id: number;
  type: PaymentMethodTypeDto;
  payload: Map<string, string>;
}
