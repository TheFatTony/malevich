import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {OrderTypeDto} from "../_transfer/orderTypeDto";

@Injectable({
  providedIn: 'root'
})
export class OrderTypeService {

  private url = environment.baseUrl + 'ordertype';

  constructor(private http: HttpClient) {
  }

  getOrderTypes() {
    return this.http
      .get<OrderTypeDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

}
