import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment.dev';
import {DocumentsDto} from '../_transfer/documentsDto';
import {DocumentTypeDto} from '../_transfer/documentTypeDto';

@Injectable({
  providedIn: 'root'
})
export class DocumentsService {

  private url = environment.baseUrl + 'document';

  constructor(private http: HttpClient) {
  }

  getDocumentTypes(userType: string) {
    return this.http.get<DocumentTypeDto[]>(this.url + '/typeList/' + userType);
  }

  getDocs() {
    return this.http.get<DocumentsDto[]>(this.url + '/list');
  }

  save(document: DocumentsDto) {
    return this.http.post(this.url + '/save', document);
  }

  deleteDocument(id: number) {
    return this.http.delete(this.url + '/delete/' + id);
  }

}
