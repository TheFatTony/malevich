import {PaymentMethodDto} from "./paymentMethodDto";

export class ExchangeOrderDto {
  id: number;

  paymentMethod: PaymentMethodDto;

  exchangeName: string;

  internalStatus: string;

  type: string;

  originalAmount: number;

  currencyPair: string;

  orderId: string;

  timestamp: Date;

  status: string;

  cumulativeAmount: number;

  averagePrice: number;

  fee: number;

  leverage: string;
}
