import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthService} from "../_services";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(public authService: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      // if (err.status === 401) {
      //     this.authService.logout();
      //     location.reload(true);
      // }
      // if (err.status === 403) {
      //   this.authService.logout();
      //   location.reload(true);
      // }

      const error = err.error.message || err.statusText;
      return throwError(error);
    }));
  }
}
