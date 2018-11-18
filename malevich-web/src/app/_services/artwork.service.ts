import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {ArtworkDto} from "../_transfer";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
import {TranslateService} from "@ngx-translate/core";

@Injectable({
  providedIn: 'root'
})
export class ArtworkService {

  private url = environment.baseUrl + 'artworks';

  constructor(private http: HttpClient,
              private translate: TranslateService) {
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

  get comboboxRenderer() {
    return (index: number, label: string, artwork: ArtworkDto) => {
      if (!artwork)
        return '';

      return '<table style="min-width: 50px;"><tr><td style="width: 50px; height: 50px;" rowspan="2">' +
        '<img class="img-fluid" src="https://via.placeholder.com/50x50/img8.jpg">' +
        '</td><td>' + '<span class="d-block g-color-gray-dark-v4">' + artwork.titleMl[this.translate.currentLang] + '</span>' + '</td></tr><tr><td>' +
        '<span class="d-block g-color-lightred">' + artwork.category.categoryNameMl[this.translate.currentLang] + '</span>' + '</td></tr></table>';
    };
  }

}
