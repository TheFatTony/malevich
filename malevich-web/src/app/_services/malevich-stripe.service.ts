import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MalevichStripeService {

  private url = environment.baseUrl + 'stripe';

  constructor(private http: HttpClient) {
  }


  pay(token: string) {
    return this.http.post<any>(this.url + `/pay/${token}`, {})
      .pipe();
  }
}
