import {Injectable} from '@angular/core';
import {BehaviorSubject, Subject} from "rxjs";
import {UserDto} from '../../node_modules/yinyang-core';

@Injectable()
export class Globals {
  isAuthorised$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  currentUser$: BehaviorSubject<UserDto> = new BehaviorSubject<UserDto>(null);

  isTrader$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  isGallery$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  isAdmin$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

}
