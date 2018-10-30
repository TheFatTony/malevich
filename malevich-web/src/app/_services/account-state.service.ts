import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {AccountStateDto} from "../_transfer/accountStateDto";
import {ArtworkStockDto} from "../_transfer/artworkStockDto";

@Injectable({
  providedIn: 'root'
})
export class AccountStateService {

  private url = environment.baseUrl + 'accountstates';

  constructor(private http: HttpClient) {
  }

  getAccountStates() {
    return this.http
      .get<AccountStateDto[]>(this.url + '/list');
  }

  getTraderWallet() {
    return this.http
      .get<AccountStateDto>(this.url + '/getTraderWallet');
  }

  getTraderArtworks() {
    return this.http
      .get<ArtworkStockDto[]>(this.url + '/getTraderArtworks');
  }

}
