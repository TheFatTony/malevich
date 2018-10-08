import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {CountryDto} from "../_transfer/countryDto";

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private url = environment.baseUrl + 'countries';

  constructor(private http: HttpClient) { }

  getCountries() {
    return this.http
      .get<CountryDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }
}
