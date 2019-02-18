import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment.dev';
import {HttpClient} from '@angular/common/http';
import {PaymentsDto} from '../_transfer/paymentsDto';

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

  getPaymentsByParticipant(participantId: number) {
    return this.http
      .get<PaymentsDto[]>(this.url + `/listByParticipant/${participantId}`);
  }

  insert(payments: PaymentsDto) {
    return this.http
      .post<PaymentsDto>(this.url + '/insert', payments);
  }

  withdraw(payments: PaymentsDto) {
    return this.http
      .post<PaymentsDto>(this.url + '/withdraw', payments);
  }


  receiptPrint(id: number) {
    const httpOptions = {
      'responseType': 'arraybuffer' as 'json'
    };

    return this.http.get<any>(this.url + '/print/' + id, httpOptions);
  }
}
