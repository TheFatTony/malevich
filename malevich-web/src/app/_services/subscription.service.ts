import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment.dev';
import {SubscriptionDto} from '../_transfer/subscriptionDto';

@Injectable({providedIn: 'root'})
export class SubscriptionService {

  private url = environment.baseUrl + 'subscribe';

  constructor(private http: HttpClient) {
  }

  save(subscribe: SubscriptionDto) {
    return this.http.post<SubscriptionDto>(this.url + '/save', subscribe);
  }

}
