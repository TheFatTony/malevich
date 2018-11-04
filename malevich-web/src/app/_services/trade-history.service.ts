import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {TradeHistoryDto} from "../_transfer/tradeHistoryDto";

@Injectable({
  providedIn: 'root'
})
export class TradeHistoryService {

  private url = environment.baseUrl + 'trade_history';

  constructor(private http: HttpClient) {
  }

  getTradeHistory() {
    return this.http
      .get<TradeHistoryDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }


  findAllByArtworkId(artworkId: number) {
    return this.http
      .get<TradeHistoryDto[]>(this.url + '/findAllByArtworkId/' + artworkId)
      .pipe(map(data => {
          return data;
        })
      );
  }

}
