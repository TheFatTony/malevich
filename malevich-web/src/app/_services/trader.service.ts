import { Injectable } from '@angular/core';
import {catchError, first, map} from "rxjs/operators";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {TraderDto} from "../_transfer/traderDto";
import {environment} from "../../environments/environment";
import {AlertService} from "./alert.service";

@Injectable({
  providedIn: 'root'
})
export class TraderService {

  private url = environment.baseUrl + 'traders';

  constructor(private http: HttpClient,
              private alertService: AlertService) { }

  getTrader() {
    return this.http
      .get<TraderDto>(this.url + '/current')
      .pipe(map(data => data));
  }

  update(trader: TraderDto) {
    return this.putTrader(trader);
  }

  // Update existing
  private putTrader(trader: TraderDto) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const url = this.url + '/update';

    return this.http
      .put<TraderDto>(url, trader)
      .pipe(first())
      .subscribe(
        data => data,
        error => this.alertService.error(error)
      );
  }
}
