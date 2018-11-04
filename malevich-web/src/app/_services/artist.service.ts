import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {ArtistDto} from "../_transfer";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ArtistService {

  private url = environment.baseUrl + 'artists';

  constructor(private http: HttpClient) {
  }

  getArtists() {
    return this.http
      .get<ArtistDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

  getArtist(id: number): Observable<ArtistDto> {
    return this.http
      .get<ArtistDto>(this.url + '/item/' + id)
      .pipe(map(data => data));
  }

}
