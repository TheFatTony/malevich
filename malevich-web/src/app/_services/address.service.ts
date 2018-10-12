import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {AddressDto} from "../_transfer/addressDto";

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private url = environment.baseUrl + 'addresses';

  constructor(private http: HttpClient) { }

  getByTrader(traderId: number) {
    return this.http
      .get<AddressDto[]>(this.url + '/trader/' + traderId)
      .pipe(map(data => data));
  }
}
