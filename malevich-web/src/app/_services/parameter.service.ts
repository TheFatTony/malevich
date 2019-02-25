import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {ParameterDto} from "../_transfer/parameterDto";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ParameterService {

  private url = environment.baseUrl + 'parameter';

  constructor(private http: HttpClient) {
  }

  getParameters() {
    return this.http
      .get<ParameterDto[]>(this.url + '/list')
      .pipe(map(data => {
        if (!data) return null;

        return data.reduce((res, cur) => {
          res[cur.id] = cur.value;
          return res;
        }, new Map<string, string>())
      }));
  }
}
