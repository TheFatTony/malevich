import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {DelayedChangeDto} from "../_transfer/delayedChangeDto";

@Injectable({
  providedIn: 'root'
})
export class DelayedChangeService {

  private url = environment.baseUrl + 'delayedChanges';

  constructor(private http: HttpClient) {
  }

  public getDelayedChanges() {
    return this.http
      .get<DelayedChangeDto[]>(this.url + '/list');
  }
participant
  public getParticipantDelayedChanges(referenceId: number) {
    return this.http
      .get<boolean>(this.url + '/findByTypeIdAndAndReferenceId/PARTICIPANT/' + referenceId);
  }

  public approveChange(delayedChangeDto: DelayedChangeDto) {
    return this.http
      .post<DelayedChangeDto>(this.url + '/approveChange', delayedChangeDto);
  }

  public declineChange(delayedChangeDto: DelayedChangeDto) {
    return this.http
      .post<DelayedChangeDto>(this.url + '/declineChange', delayedChangeDto);
  }

}
