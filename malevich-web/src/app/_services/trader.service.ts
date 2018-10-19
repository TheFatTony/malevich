import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TraderDto} from "../_transfer/traderDto";
import {environment} from "../../environments/environment";
import {AlertService} from "./alert.service";

@Injectable({
  providedIn: 'root'
})
export class TraderService {

  private url = environment.baseUrl + 'traders';

  constructor(private http: HttpClient,
              private alertService: AlertService) {
  }

  getTrader() {
    return this.http
      .get<TraderDto>(this.url + '/current');
  }

  update(trader: TraderDto) {
    return this.putTrader(trader);
  }

  create(trader: TraderDto, activationCode: string) {
    return this.postTrader(trader, activationCode);
  }

  // Update existing
  private putTrader(trader: TraderDto) {
    const url = this.url + '/update';

    return this.http
      .put<TraderDto>(url, trader)
      .subscribe(
        data => data,
        error => this.alertService.error(error)
      );
  }

  private postTrader(trader: TraderDto, activationCode: string) {
    const url = this.url + '/insert/' + activationCode;

    return this.http
      .post<TraderDto>(url, trader)
      .subscribe(
        data => data,
        error => this.alertService.error(error)
      );
  }
}
