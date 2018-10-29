import {PaymentMethodDto} from "./paymentMethodDto";

export class PaymentsDto {
  id: number;
  effectiveDate: Date;
  paymentMethod: PaymentMethodDto;
  amount: number;
}
