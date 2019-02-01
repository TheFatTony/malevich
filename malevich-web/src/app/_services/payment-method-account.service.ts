import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {PaymentMethodDto} from "../_transfer/paymentMethodDto";

@Injectable({
  providedIn: 'root'
})
export class PaymentMethodAccountService {

  private url = environment.baseUrl + 'payment_methods_account';

  constructor(private http: HttpClient) {
  }

  getPaymentMethods() {
    return this.http
      .get<PaymentMethodDto[]>(this.url + '/list');
  }

  save(dto: PaymentMethodDto) {
    return this.http
      .put<PaymentMethodDto>(this.url + '/save', dto);
  }
}
