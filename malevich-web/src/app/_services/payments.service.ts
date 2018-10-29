import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {PaymentsDto} from "../_transfer/paymentsDto";

@Injectable({
  providedIn: 'root'
})
export class PaymentsService {
  private url = environment.baseUrl + 'payments';

  constructor(private http: HttpClient) {
  }

  getPayments() {
    return this.http
      .get<PaymentsDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

  insert(payments: PaymentsDto) {
    return this.http
      .post<PaymentsDto>(this.url + '/insert', payments)
      .pipe(map(data => {
          return data;
        })
      );
  }
}
