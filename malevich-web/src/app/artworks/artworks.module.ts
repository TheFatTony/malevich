import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';


import {ArtworksListComponent} from './artworks-list/artworks-list.component';
import {FiltersComponent} from './artworks-list/filters/filters.component';
import {GridComponent} from './artworks-list/grid/grid.component';
import {ListComponent} from './artworks-list/list/list.component';
import {HttpClient} from "@angular/common/http";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {createTranslateLoader} from "../app.module";

@NgModule({
  imports: [
    CommonModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
  ],
  declarations: [
    ArtworksListComponent,
    FiltersComponent,
    GridComponent,
    ListComponent]
})
export class ArtworksModule {
}
