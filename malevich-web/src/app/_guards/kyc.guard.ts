import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, RouterStateSnapshot} from '@angular/router';
import {KycLevelService} from "../_services/kyc-level.service";
import {Observable, Subject} from "rxjs";
import {ParticipantService} from "../_services/participant.service";
import {mergeMap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class KycGuard implements CanActivate, CanActivateChild {

  constructor(private participantService: ParticipantService,
              private kycLevelService: KycLevelService) {

  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | boolean {
    return this.canActivateInternal(route, state);
  }

  canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | boolean {
    return this.canActivateInternal(route, state);
  }

  private canActivateInternal(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | boolean {
    const levels = route.data['kycLevels'] as string[];

    if (!levels || !levels.length)
      return true;

    return this.participantService.getCurrent()
      .pipe(mergeMap(participant => {

        if (!participant) {
          const emitter = new Subject<boolean>();
          emitter.next(false);
          return emitter;
        }

        return this.kycLevelService.testLevel(participant.kycLevel.id, levels);
      }));
  }
}
