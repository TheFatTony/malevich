import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {TradeTypeDto} from "../_transfer/tradeTypeDto";

@Injectable({
  providedIn: 'root'
})
export class TradeTypeService {

  private url = environment.baseUrl + 'tradetype';

  constructor(private http: HttpClient) {
  }

  getTradeTypes() {
    return this.http
      .get<TradeTypeDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

}
