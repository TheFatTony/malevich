import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from "rxjs/operators";
import {LoadingService} from "../_services/loading.service";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  private requestsCount: number = 0;

  constructor(private loadingService: LoadingService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    console.info("intercept");
    this.showLoader();

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
