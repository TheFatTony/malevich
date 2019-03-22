import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private url = environment.baseUrl + 'admin';

  constructor(private http: HttpClient) {
  }

  sendAllMail() {
    return this.http.get(this.url + '/scheduling/sendAllMail');
  }

  checkBalance() {
    return this.http.get(this.url + '/scheduling/checkBalance');
  }

  marketOrdersCheck() {
    return this.http.get(this.url + '/scheduling/marketOrdersCheck');
  }

  revolutDepositCheck() {
    return this.http.get(this.url + '/scheduling/revolutDepositCheck');
  }

  sendAllMessages() {
    return this.http.get(this.url + '/scheduling/sendAllMessages');
  }


}
