import { Injectable } from '@angular/core';
import {map} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";
import {TraderDto} from "../_transfer/traderDto";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TraderService {

  private url = environment.baseUrl + 'traders';

  constructor(private http: HttpClient) { }

  getTrader() {
    return this.http
      .get<TraderDto>(this.url + '/current')
      .pipe(map(data => data));
  }
}
