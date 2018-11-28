import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {DelayedChangeDto} from "../_transfer/delayedChangeDto";
import {TraderDto} from "../_transfer/traderDto";

@Injectable({
  providedIn: 'root'
})
export class DelayedChangeService {

  private url = environment.baseUrl + 'delayedChanges';

  constructor(private http: HttpClient) {
  }

  public getDelayedChanges() {
    return this.http
      .get<DelayedChangeDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

  public approveChange(delayedChangeDto: DelayedChangeDto) {
    return this.http
      .post<TraderDto>(this.url + '/approveChange', delayedChangeDto);
  }

}
