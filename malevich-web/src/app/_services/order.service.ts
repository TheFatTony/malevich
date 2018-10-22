import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {OrderDto} from "../_transfer/orderDto";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url = environment.baseUrl + 'order';

  constructor(private http: HttpClient) {
  }

  getPlacedOrders() {
    return this.http
      .get<OrderDto[]>(this.url + '/getPlacedOrders')
      .pipe(map(data => data));
  }
}
