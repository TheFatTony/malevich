import {PaymentMethodDto} from './paymentMethodDto';
import {PaymentTypeDto} from './paymentTypeDto';
import {ParticipantDto} from "./participantDto";

export class PaymentsDto {
  id: number;
  participant: ParticipantDto;
  effectiveDate: Date;
  paymentMethod: PaymentMethodDto;
  amount: number;
  paymentType: PaymentTypeDto;
  transactionId: string;
}
