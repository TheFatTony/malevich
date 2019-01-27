import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {CommissionRuleDto} from "../_transfer/commissionRuleDto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CommissionRuleService {

  private url = environment.baseUrl + 'commission';

  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http
      .get<CommissionRuleDto[]>(this.url + '/list');
  }

  get(id: number): Observable<CommissionRuleDto> {
    return this.http
      .get<CommissionRuleDto>(this.url + '/item/' + id);
  }

  save(dto: CommissionRuleDto): Observable<CommissionRuleDto> {
    return this.http
      .post<CommissionRuleDto>(this.url + '/save', dto);
  }
}
