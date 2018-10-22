import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {ArtworkStockDto} from "../_transfer/artworkStockDto";

@Injectable({
  providedIn: 'root'
})
export class ArtworkStockService {

  private url = environment.baseUrl + 'artworkstock';

  constructor(private http: HttpClient) {
  }

  getArtworkStock() {
    return this.http
      .get<ArtworkStockDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }
}
