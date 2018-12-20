import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {CounterpartyDto} from "../_transfer/counterpartyDto";

@Injectable({
  providedIn: 'root'
})
export class CounterpartyService {

  private url = environment.baseUrl + 'counterparty';

  constructor(private http: HttpClient) {
  }

  getCurrent() {
    return this.http
      .get<CounterpartyDto>(this.url + '/current');
  }

  update(dto: CounterpartyDto) {
    const url = this.url + '/update';
    return this.http.put(url, dto)
  }

}
