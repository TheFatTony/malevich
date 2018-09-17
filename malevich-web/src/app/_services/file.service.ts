import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';

import { File } from '../_transfer';
import {environment} from '../../environments/environment';
import {throwError} from 'rxjs';

@Injectable()
export class FileService {

  private url = environment.baseUrl + 'files';

  constructor(private http: HttpClient) {}

  getFiles() {
    return this.http
      .get<File[]>(this.url + '/list')
      .pipe(map(data => data));
  }

}
