import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {PaymentMethodDto} from "../_transfer/paymentMethodDto";

@Injectable({
  providedIn: 'root'
})
export class PaymentMethodDepositReferenceService {

  private url = environment.baseUrl + 'payment_methods_deposit_reference';

  constructor(private http: HttpClient) {
  }

  getPaymentMethods() {
    return this.http
      .get<PaymentMethodDto[]>(this.url + '/list');
  }

  get() {
    return this.http.get<PaymentMethodDto>(this.url + '/get');
  }
}
