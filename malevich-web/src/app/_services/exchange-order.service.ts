import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {ExchangeOrderDto} from "../_transfer/exchangeOrderDto";

@Injectable({
  providedIn: 'root'
})
export class ExchangeOrderService {

  private url = environment.baseUrl + 'exchange_order';

  constructor(private http: HttpClient) {
  }

  getExchangeOrders() {
    return this.http.get<ExchangeOrderDto[]>(this.url + '/list');
  }
}
