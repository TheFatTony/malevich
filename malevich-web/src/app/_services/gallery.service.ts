import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {GalleryDto} from "../_transfer";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GalleryService {

  private url = environment.baseUrl + 'galleries';

  constructor(private http: HttpClient) {
  }

  getGalleries() {
    return this.http
      .get<GalleryDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

  getGallery(id: number): Observable<GalleryDto> {
    return this.http
      .get<GalleryDto>(this.url + '/item/' + id)
      .pipe(map(data => data));
  }

}
