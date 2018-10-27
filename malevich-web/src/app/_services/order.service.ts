import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {OrderDto} from "../_transfer/orderDto";
import {GalleryDto} from "../_transfer";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url = environment.baseUrl + 'orders';

  constructor(private http: HttpClient) {
  }

  getOrders() {
    return this.http
      .get<OrderDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

  getPlacedOrders() {
    return this.http
      .get<OrderDto[]>(this.url + '/getPlacedOrders')
      .pipe(map(data => data));
  }

  placeBid(order: OrderDto) {
    return this.http
      .post<GalleryDto>(this.url + '/placeBid', order)
      .pipe(map(data => {
          return data;
        })
      );
  }
}
