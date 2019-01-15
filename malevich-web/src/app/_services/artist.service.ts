import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {ArtistDto} from "../_transfer";
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
      .get<ArtistDto[]>(this.url + '/list');
  }

  getArtist(id: number): Observable<ArtistDto> {
    return this.http
      .get<ArtistDto>(this.url + '/item/' + id);
  }

  saveArtist(artist: ArtistDto): Observable<ArtistDto> {
    return this.http
      .post<ArtistDto>(this.url + '/save', artist);
  }

}
