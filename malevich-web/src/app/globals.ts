import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {UserDto} from "./_transfer";

@Injectable()
export class Globals {
  isAuthorised = false;

  currentUser$: Subject<UserDto> = new Subject<UserDto>();

}
