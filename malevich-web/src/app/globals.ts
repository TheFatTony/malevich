import {Injectable} from '@angular/core';
import {Observable, of, Subject} from "rxjs";
import {UserDto} from "./_transfer";

@Injectable()
export class Globals {
  isAuthorised = false;

  currentUser$: Subject<UserDto> = new Subject<UserDto>();

}
