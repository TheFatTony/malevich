import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {DelayedChangeDto} from "../_transfer/delayedChangeDto";
import {AlertService} from "yinyang-core";

@Injectable({
  providedIn: 'root'
})
export class DelayedChangeService {

  private url = environment.baseUrl + 'delayedChanges';

  constructor(private http: HttpClient,
              private alertService: AlertService) {
  }

  public getDelayedChanges() {
    return this.http
      .get<DelayedChangeDto[]>(this.url + '/list');
  }

  public checkDelayedChanges(type: string, referenceId: number) {
    return this.http
      .get<boolean>(this.url + `/findByTypeIdAndAndReferenceId/${type}/${referenceId}`);
  }

  public alertIfDelayedChanges(type: string, referenceId: number) {
    return this.checkDelayedChanges(type, referenceId)
      .pipe(map(data => {
        if (data === true)
          this.alertService.success("You have unprocessed changes.");
        return data;
      }));
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
