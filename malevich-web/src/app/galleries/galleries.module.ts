import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpClient} from "@angular/common/http";
import {createTranslateLoader} from "../app.module";

import {GalleriesListComponent} from "./galleries-list/galleries-list.component";
import {FiltersComponent} from "./galleries-list/filters/filters.component";
import {GridComponent} from "./galleries-list/grid/grid.component";
import {ListComponent} from "./galleries-list/list/list.component";

@NgModule({
  imports: [
    CommonModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    })
  ],
  declarations: [
    GalleriesListComponent,
    FiltersComponent,
    GridComponent,
    ListComponent]
})
export class GalleriesModule {
}
