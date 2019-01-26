import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
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

  getWallet() {
    return this.http
      .get<AccountStateDto>(this.url + '/getWallet');
  }



}
