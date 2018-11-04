import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
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

  getGalleryById(id: number): Observable<GalleryDto> {
    return this.http
      .get<GalleryDto>(this.url + '/item/' + id)
      .pipe(map(data => data));
  }

  getGallery(): Observable<GalleryDto> {
    return this.http
      .get<GalleryDto>(this.url + '/current')
      .pipe(map(data => data));
  }

  updateGallery(gallery: GalleryDto) {
    return this.http
      .post<GalleryDto>(this.url + '/update', gallery)
      .pipe(map(data => {
          return data;
        })
      );
  }

}
