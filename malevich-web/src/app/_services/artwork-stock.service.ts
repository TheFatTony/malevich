import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment.dev';
import {HttpClient} from '@angular/common/http';
import {ArtworkStockDto} from '../_transfer/artworkStockDto';
import {map} from 'rxjs/internal/operators';
import {TranslateService} from '../../../node_modules/@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class ArtworkStockService {

  private url = environment.baseUrl + 'artworkstock';

  constructor(private http: HttpClient,
              private translate: TranslateService) {
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

  getOwnArtworks() {
    return this.http
      .get<ArtworkStockDto[]>(this.url + '/getOwnArtworks');
  }

  getStoredArtworks() {
    return this.http
      .get<ArtworkStockDto[]>(this.url + '/getStoredArtworks');
  }

  addArtworkStock(artworkStock: ArtworkStockDto) {
    return this.http
      .post<ArtworkStockDto>(this.url + '/add', artworkStock);
  }

  updateArtworkStock(artworkStock: ArtworkStockDto) {
    return this.http
      .put<ArtworkStockDto>(this.url + '/update', artworkStock);
  }

  deleteArtworkStock(id: number) {
    return this.http
      .delete<ArtworkStockDto>(this.url + '/delete/' + id);
  }

  get comboboxRenderer() {
    return (index: number, label: string, artworkStock: ArtworkStockDto) => {
      if (!artworkStock)
        return '(null)';

      return '<table style="min-width: 50px;"><tr><td style="width: 50px; height: 50px;" rowspan="2">' +
        '<img class="img-fluid" src="https://via.placeholder.com/50x50/img8.jpg">' +
        '</td><td>' + '<span class="d-block g-color-gray-dark-v4">' + artworkStock.artwork.titleMl[this.translate.currentLang] + '</span>' + '</td></tr><tr><td>' +
        '<span class="d-block g-color-lightred">' + artworkStock.artwork.category.categoryNameMl[this.translate.currentLang] + '</span>' + '</td></tr></table>';
    };
  }

}
