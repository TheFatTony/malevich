import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {ArtistDto} from "../_transfer";
import {map} from "rxjs/operators";

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
}
