import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {Globals} from '../globals';
import {UserDto} from '../_transfer';

@Injectable({providedIn: 'root'})
export class AuthService {

  private url = environment.baseUrl + 'auth';

  constructor(private http: HttpClient, public globals: Globals) {
  }

  login(username: string, password: string) {
    return this.http.post<any>(this.url + '/authenticate', {username: username, password: password})
      .pipe(map(user => {
        if (user && user.token) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.globals.isAuthorised = true;
          console.log(this.globals.isAuthorised);
          this.getUser();
        }

        return user;
      }));
  }

  refreshToken() {
    if (localStorage.getItem('currentUser')) {
      this.globals.isAuthorised = true;
      console.log(this.globals.isAuthorised);
      this.getUser();
      if (!localStorage.getItem('user')) {
        this.logout();
      }
    }
  }

  getUser() {
    this.http.get<UserDto>(this.url).subscribe(user => {
      localStorage.setItem('user', JSON.stringify(user));
    });
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('user');
    this.globals.isAuthorised = false;
    console.log(this.globals.isAuthorised);
  }

}
