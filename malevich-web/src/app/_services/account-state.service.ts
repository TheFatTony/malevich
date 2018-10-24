import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {AccountStateDto} from "../_transfer/accountStateDto";

@Injectable({
  providedIn: 'root'
})
export class AccountStateService {

  private url = environment.baseUrl + 'accountstates';

  constructor(private http: HttpClient) {
  }

  getAccountStates() {
    return this.http
      .get<AccountStateDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }
}
