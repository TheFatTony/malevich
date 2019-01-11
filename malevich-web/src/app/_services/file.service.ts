import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

import {environment} from '../../environments/environment.dev';
import {FileDto} from '../../../node_modules/yinyang-core';

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
