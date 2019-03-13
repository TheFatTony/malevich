import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OrderWindowComponent} from "./order-window/order-window.component";
import {ArtworkEditComponent} from "./artwork-edit/artwork-edit.component";
import {YinyangCoreModule} from "yinyang-core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpClient} from "@angular/common/http";
import {createTranslateLoader} from "../../app.module";
import {OrderEditComponent} from './order-edit/order-edit.component';
import { PagesNavigationComponent } from './pages-navigation/pages-navigation.component';

@NgModule({
  declarations: [
    OrderWindowComponent,
    ArtworkEditComponent,
    OrderEditComponent,
    PagesNavigationComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    YinyangCoreModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
  ],
  exports: [
    OrderWindowComponent,
    OrderEditComponent,
    ArtworkEditComponent,
    PagesNavigationComponent
  ]
})
export class CommonComponentsModule {
}
