import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {GenderDto} from "../_transfer/genderDto";

@Injectable({
  providedIn: 'root'
})
export class GenderService {

  private url = environment.baseUrl + 'genders';

  constructor(private http: HttpClient) {
  }

  getGenders() {
    return this.http
      .get<GenderDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }
}
