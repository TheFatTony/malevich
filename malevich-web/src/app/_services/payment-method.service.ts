import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {PaymentMethodDto} from "../_transfer/paymentMethodDto";

@Injectable({
  providedIn: 'root'
})
export class PaymentMethodService {

  private url = environment.baseUrl + 'payment_methods';

  constructor(private http: HttpClient) {
  }

  getPaymentMethods() {
    return this.http
      .get<PaymentMethodDto[]>(this.url + '/list');
  }

  saveCard(dto: PaymentMethodDto) {
    return this.http
      .put<PaymentMethodDto[]>(environment.baseUrl + 'payment_methods_card/save', dto);
  }
}
