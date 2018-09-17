import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {Globals} from '../globals';
import {User} from '../_transfer';

@Injectable()
export class LoginService {

  private url = environment.baseUrl + 'users';

  constructor(private http: HttpClient, public globals: Globals) {
  }

  login(username: string, password: string) {
    return this.http.post<any>(this.url + '/authenticate', {username: username, password: password})
      .pipe(map(user => {
        if (user && user.token) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.globals.isAuthorised = true;
          this.refreshToken();
        }

        return user;
      }));
  }

  refreshToken() {
    this.http.get<User>(this.url).subscribe(user => {
      localStorage.setItem('user', JSON.stringify(user));
    });
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('user');
    this.globals.isAuthorised = false;
  }

}
