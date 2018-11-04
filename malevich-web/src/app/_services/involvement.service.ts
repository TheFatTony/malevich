import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {InvolvementDto} from "../_transfer";
import {map} from "rxjs/operators";
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class InvolvementService {

  private url = environment.baseUrl + 'counters';

  constructor(private http: HttpClient) {
  }

  getInvolvementCounters(): Observable<InvolvementDto> {
    return this.http
      .get<InvolvementDto>(this.url + '/involvementCounters')
      .pipe(map(data => data));
  }

}
