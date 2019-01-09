import {PaymentMethodDto} from './paymentMethodDto';
import {PaymentTypeDto} from './paymentTypeDto';

export class PaymentsDto {
  id: number;
  effectiveDate: Date;
  paymentMethod: PaymentMethodDto;
  amount: number;
  paymentType: PaymentTypeDto;
}
