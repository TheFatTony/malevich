import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment.dev';
import {Globals} from '../globals';
import {UserDto} from '../_transfer';
import {Observable, of} from "rxjs";

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
          this.getUser();
        }

        return user;
      }));
  }

  refreshToken() {
    if (localStorage.getItem('currentUser')) {
      this.globals.isAuthorised = true;
      this.getUser();
      if (!localStorage.getItem('user')) {
        this.logout();
      }
    }
  }

  getUser() {
    this.http.get<UserDto>(this.url)
      .subscribe((user: UserDto) => {
        this.globals.currentUser$.next(user);
        localStorage.setItem('user', JSON.stringify(user));
      });
  }

  getCurrentUser(): Observable<UserDto> {
    return this.globals.currentUser$;
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('user');
    this.globals.isAuthorised = false;
  }

  register(lang: string, email: string) {
    return this.http.post<any>(this.url + '/register', {lang: lang, email: email});
  }

  /// set password
  register2(token: string, password: string) {
    return this.http.post<any>(this.url + `/register/${token}`, {password: password});
  }

  reset(lang: string, email: string) {
    return this.http.post<any>(this.url + '/reset', {lang: lang, email: email});
  }

  setNewPassword(token: string, password: string) {
    return this.http.post<any>(this.url + `/reset/${token}`, {password: password});
  }

  changePassword(password: string, newPassword: string) {
    return this.http.post<any>(this.url + '/changePassword', {password: password, newPassword: newPassword});
  }

}
