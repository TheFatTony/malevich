import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment.dev';
import {HttpClient} from '@angular/common/http';
import {ArtworkStockDto} from '../_transfer/artworkStockDto';
import {map} from 'rxjs/internal/operators';

@Injectable({
  providedIn: 'root'
})
export class ArtworkStockService {

  private url = environment.baseUrl + 'artworkstock';

  constructor(private http: HttpClient) {
  }

  getArtworkStocks() {
    return this.http
      .get<ArtworkStockDto[]>(this.url + '/list');
  }

  stocksByFilter(filterDto: any) {
    return this.http.post(this.url + '/filter', filterDto, {observe: 'response'}).pipe(map((response: any) => response));
  }

  getArtworkStock(id: number) {
    return this.http
      .get<ArtworkStockDto>(this.url + '/item/' + id);
  }

  addArtworkStock(artworkStock: ArtworkStockDto) {
    return this.http
      .post<ArtworkStockDto>(this.url + '/add', artworkStock);
  }

  deleteArtworkStock(id: number) {
    return this.http
      .delete<ArtworkStockDto>(this.url + '/delete/' + id);
  }

}
