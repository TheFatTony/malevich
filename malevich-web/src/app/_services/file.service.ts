import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

import {FileDto} from '../_transfer';
import {environment} from '../../environments/environment.dev';

@Injectable()
export class FileService {

  private url = environment.baseUrl + 'files';

  constructor(private http: HttpClient) {
  }

  getFiles() {
    return this.http
      .get<FileDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

}
