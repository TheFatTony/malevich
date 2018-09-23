import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ArtistsListComponent} from "./artists-list/artists-list.component";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpClient} from "@angular/common/http";
import {createTranslateLoader} from "../app.module";
import { FiltersComponent } from './artists-list/filters/filters.component';
import { GridComponent } from './artists-list/grid/grid.component';
import { ListComponent } from './artists-list/list/list.component';

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
  declarations: [ArtistsListComponent, FiltersComponent, GridComponent, ListComponent]
})
export class ArtistsModule { }
