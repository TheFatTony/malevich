import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {Globals} from "../globals";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = environment.baseUrl + 'user';

  constructor(private http: HttpClient, public globals: Globals) {
  }

  register(lang: string, email: string) {
    return this.http.post<any>(this.url + '/register', {lang: lang, email: email});
  }

  /// set password
  register2(token: string, info: any) {
    return this.http.post<any>(this.url + `/register/${token}`, info);
  }

  reset(lang: string, email: string) {
    return this.http.post<any>(this.url + '/password/reset', {lang: lang, email: email});
  }

  setNewPassword(token: string, password: string) {
    return this.http.post<any>(this.url + `/password/reset/${token}`, {password: password});
  }

  changePassword(password: string, newPassword: string) {
    return this.http.post<any>(this.url + '/password/change', {password: password, newPassword: newPassword});
  }
}
