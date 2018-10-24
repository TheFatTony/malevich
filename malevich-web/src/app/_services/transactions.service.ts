import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {TransactionsDto} from "../_transfer/transactionsDto";

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {
  private url = environment.baseUrl + 'transactions';

  constructor(private http: HttpClient) {
  }

  getTransactions() {
    return this.http
      .get<TransactionsDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }
}
