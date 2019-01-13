import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment.dev';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {UserPasswordDto} from '../../_transfer/userPasswordDto';
import {UserDto} from '../../../../node_modules/yinyang-core';

@Injectable()
export class UsersService {

  private url = environment.baseUrl + 'user';

  constructor(private http: HttpClient) {
  }

  getUsers() {
    return this.http
      .get<UserDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

  lockUser(user: UserDto) {
    return this.http.post<UserDto>(this.url + '/lock', user);
  }

  setPassword(userPassword: UserPasswordDto) {
    return this.http.post<UserPasswordDto>(this.url + '/password/set', userPassword);
  }

}
