import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {first, map} from "rxjs/operators";
import {AddressDto} from "../_transfer/addressDto";
import {TraderDto} from "../_transfer/traderDto";
import {AlertService} from "./alert.service";

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private url = environment.baseUrl + 'addresses';

  constructor(private http: HttpClient,
              private alertService: AlertService) { }

  getByTrader(traderId: number) {
    return this.http
      .get<AddressDto[]>(this.url + '/trader/' + traderId)
      .pipe(map(data => data));
  }
}

