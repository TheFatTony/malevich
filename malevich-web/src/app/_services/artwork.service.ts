import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {ArtworkDto} from "../_transfer";
import {map} from "rxjs/operators";

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
}
