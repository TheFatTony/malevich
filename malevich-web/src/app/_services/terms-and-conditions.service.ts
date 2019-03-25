import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {TermsAndConditionsDto} from "../_transfer/termsAndConditions";
import {UserTypeDto} from "yinyang-core";

@Injectable({
  providedIn: 'root'
})
export class TermsAndConditionsService {

  private url = environment.baseUrl + 'termsAndConditions';

  constructor(private http: HttpClient) {
  }

  getHtml(usertType: UserTypeDto) {
    return this.http.post<string>(this.url, usertType);
  }

}
