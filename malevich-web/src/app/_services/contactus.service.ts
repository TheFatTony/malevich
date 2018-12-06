import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment.dev';
import {ContactUsDto} from '../_transfer/contactUsDto';

@Injectable({providedIn: 'root'})
export class ContactUsService {

  private url = environment.baseUrl + 'contactus';

  constructor(private http: HttpClient) {
  }

  save(contactUs: ContactUsDto) {
    return this.http.post<any>(this.url + '/save', contactUs);
  }

}
