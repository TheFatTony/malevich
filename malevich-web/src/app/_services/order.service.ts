import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {OrderDto} from "../_transfer/orderDto";
import {OrderPublicDto} from "../_transfer/orderPublicDto";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url = environment.baseUrl + 'orders';

  constructor(private http: HttpClient) {
  }

  getOrders() {
    return this.http
      .get<OrderDto[]>(this.url + '/list');
  }

  getPlacedOrders() {
    return this.http
      .get<OrderDto[]>(this.url + '/getPlacedOrders');
  }

  placeAsk(order: OrderDto) {
    return this.http
      .post<OrderDto>(this.url + '/placeAsk', order);
  }

  placeBid(order: OrderDto) {
    return this.http
      .post<OrderDto>(this.url + '/placeBid', order);
  }

  getOrdersByArtworkId(artworkId: number) {
    return this.http
      .get<OrderPublicDto[]>(this.url + '/getOrdersByArtworkId/' + artworkId);
  }


}
