import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {MainHeaderComponent} from './main/main-header/main-header.component';
import {MainFooterComponent} from './main/main-footer/main-footer.component';
import {MainPageComponent} from './main/main-page/main-page.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import {AdminModule} from "./admin/admin.module";
import {NgxPaginationModule} from "ngx-pagination";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {AlertComponent} from "./_directives/alert/alert.component";
import {Globals} from "./globals";
import {AlertService, FileService, LoginService} from "./_services";
import {AuthGuard} from "./_guards/auth.guard";
import {AdminGuard} from "./_guards/admin.guard";
import {ErrorInterceptor, JwtInterceptor} from "./_helpers";
import { HelpComponent } from './main/help/help.component';
import { AboutComponent } from './main/about/about.component';
import { ContactComponent } from './main/contact/contact.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ResetComponent } from './auth/reset/reset.component';
import { TraderProfileComponent } from './profile/trader-profile/trader-profile.component';
import { ArtworksListComponent } from './artworks/artworks-list/artworks-list.component';
import { GalleriesListComponent } from './galleries/galleries-list/galleries-list.component';
import { ArtistsListComponent } from './artists/artists-list/artists-list.component';

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    AlertComponent,
    MainHeaderComponent,
    MainFooterComponent,
    MainPageComponent,
    HelpComponent,
    AboutComponent,
    ContactComponent,
    LoginComponent,
    RegisterComponent,
    ResetComponent,
    TraderProfileComponent,
    ArtworksListComponent,
    GalleriesListComponent,
    ArtistsListComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgxPaginationModule,
    AdminModule
  ],
  providers: [Globals, LoginService, FileService, AlertService, AuthGuard, AdminGuard,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
