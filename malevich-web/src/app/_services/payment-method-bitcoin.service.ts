import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {PaymentMethodDto} from "../_transfer/paymentMethodDto";

@Injectable({
  providedIn: 'root'
})
export class PaymentMethodBitcoinService {

  private url = environment.baseUrl + 'payment_methods_bitcoin';

  constructor(private http: HttpClient) {
  }

  getPaymentMethods() {
    return this.http
      .get<PaymentMethodDto[]>(this.url + '/list');
  }

  generateBtc() {
    return this.http.get<PaymentMethodDto>(this.url + '/generate');
  }
}
