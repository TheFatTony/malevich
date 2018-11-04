import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {PaymentMethodTypeDto} from "../_transfer/paymentMethodTypeDto";

@Injectable({
  providedIn: 'root'
})
export class PaymentMethodTypeService {

  private url = environment.baseUrl + 'payment_method_types';

  constructor(private http: HttpClient) {
  }

  getPaymentMethodType() {
    return this.http
      .get<PaymentMethodTypeDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }
}
