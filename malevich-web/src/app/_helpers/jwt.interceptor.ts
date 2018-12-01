import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from "rxjs/operators";
import {AlertService, LoadingService} from "yinyang-core";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  private requestsCount: number = 0;

  constructor(private loadingService: LoadingService,
              private alertService: AlertService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.showLoader();

    // if we already have a content-type, do not
    // set it, but if we don't have one, set it to
    // default --> json
    if (!request.headers.has('Content-Type')) {
      request = request.clone({headers: request.headers.set('Content-Type', 'application/json')});
    }

    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser && currentUser.token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${currentUser.token}`
        }
      });
    }

    return next.handle(request).pipe(tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          this.hideLoader();
        }
      },
      (err: any) => {
        this.alertService.error(`Request to '${request.url}' failed. ` + err);
        this.hideLoader();
      }));
  }

  private hideLoader(): void {
    this.requestsCount--;
    if (this.requestsCount === 0) {
      this.loadingService.hide();
    }
  }

  private showLoader(): void {
    this.requestsCount++;
    this.loadingService.show();
  }

}
