import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.dev";
import {first, map} from "rxjs/operators";
import {AddressDto} from "../_transfer/addressDto";
import {AlertService} from "yinyang-core";

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private url = environment.baseUrl + 'addresses';

  constructor(private http: HttpClient,
              private alertService: AlertService) {
  }

  getByTrader(traderId: number) {
    return this.http
      .get<AddressDto[]>(this.url + '/trader/' + traderId)
      .pipe(map(data => data));
  }

  create(address: AddressDto) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const url = this.url + '/insert';

    return this.http
      .post<AddressDto>(url, address)
      .pipe(first())
      .subscribe(
        data => data,
        error => this.alertService.error(error)
      );
  }

}
