import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {UserDto} from '../../node_modules/yinyang-core';

@Injectable()
export class Globals {
  isAuthorised = false;

  currentUser$: Subject<UserDto> = new Subject<UserDto>();

  isTrader$: Subject<boolean> = new Subject<boolean>();

  isGallery$: Subject<boolean> = new Subject<boolean>();

  isTrader: boolean;

  isGallery: boolean;

}
