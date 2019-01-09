import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {TermsAndConditionsDto} from "../_transfer/termsAndConditions";

@Injectable({
  providedIn: 'root'
})
export class TermsAndConditionsService {

  private url = environment.baseUrl + 'termsAndConditions';

  constructor(private http: HttpClient) {
  }

  getHtml(lang: string) {
    return this.http.get<TermsAndConditionsDto>(this.url + '/' + lang);
  }

}
