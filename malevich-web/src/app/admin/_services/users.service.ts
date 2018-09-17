import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {User} from '../../_transfer';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class UsersService {

  private url = environment.baseUrl + 'users';

  constructor(private http: HttpClient) { }

  getUsers() {
    return this.http
      .get<User[]>(this.url + '/list')
      .pipe(map(data => data));
  }

}
