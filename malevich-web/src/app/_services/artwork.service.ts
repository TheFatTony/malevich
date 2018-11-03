import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {ArtworkDto} from "../_transfer";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ArtworkService {

  private url = environment.baseUrl + 'artworks';

  constructor(private http: HttpClient) {
  }

  getArtworks() {
    return this.http
      .get<ArtworkDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

  getArtwork(id: number): Observable<ArtworkDto> {
    return this.http
      .get<ArtworkDto>(this.url + '/item/' + id)
      .pipe(map(data => data));
  }

}
