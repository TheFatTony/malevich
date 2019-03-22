import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment.dev';
import {HttpClient} from '@angular/common/http';
import {PaymentsDto} from '../_transfer/paymentsDto';
import {BalanceHistoryDto} from "../_transfer/balabceHistoryDto";

@Injectable({
  providedIn: 'root'
})
export class PaymentsService {
  private url = environment.baseUrl + 'payments';

  constructor(private http: HttpClient) {
  }

  getPayments() {
    return this.http
      .get<PaymentsDto[]>(this.url + '/list');
  }

  getPayments1() {
    return this.http
      .get<BalanceHistoryDto[]>(this.url + '/list1');
  }

  getPaymentsByParticipant(participantId: number) {
    return this.http
      .get<PaymentsDto[]>(this.url + `/listByParticipant/${participantId}`);
  }

  insert(payments: PaymentsDto) {
    return this.http
      .post<PaymentsDto>(this.url + '/insert', payments);
  }

  insertAdmin(payments: PaymentsDto) {
    return this.http
      .post<PaymentsDto>(this.url + '/insertAdmin', payments);
  }

  receiptPrint(id: number) {
    const httpOptions = {
      'responseType': 'arraybuffer' as 'json'
    };

    return this.http.get<any>(this.url + '/print/' + id, httpOptions);
  }
}
