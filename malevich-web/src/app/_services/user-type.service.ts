import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {Globals} from "../globals";
import {UserTypeDto} from "../_transfer/userTypeDto";

@Injectable({
  providedIn: 'root'
})
export class UserTypeService {

  private url = environment.baseUrl + 'userType';

  constructor(private http: HttpClient, public globals: Globals) {
  }

  getAll() {
    return this.http
      .get<UserTypeDto[]>(this.url + '/list');
  }
}
