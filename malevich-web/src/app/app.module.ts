import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {MainHeaderComponent} from './main/main-header/main-header.component';
import {MainFooterComponent} from './main/main-footer/main-footer.component';
import {MainPageComponent} from './main/main-page/main-page.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {AdminModule} from './admin/admin.module';
import {NgxPaginationModule} from 'ngx-pagination';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {Globals} from './globals';
import {FileService} from './_services';
import {ErrorInterceptor, JwtInterceptor, YinyangCoreModule, AuthGuard, AdminGuard} from '../../node_modules/yinyang-core';
import {HelpComponent} from './main/help/help.component';
import {AboutComponent} from './main/about/about.component';
import {ContactComponent} from './main/contact/contact.component';
import {LoginComponent} from './auth/login/login.component';
import {RegisterComponent} from './auth/register/register.component';
import {ResetComponent} from './auth/reset/reset.component';

import {ArtworksListComponent} from './artworks/artworks-list/artworks-list.component';
import {FiltersComponent as ArtworksListFiltersComponent} from './artworks/artworks-list/filters/filters.component';
import {GridComponent as ArtworksListGridComponent} from './artworks/artworks-list/grid/grid.component';
import {ListComponent as ArtworksListListComponent} from './artworks/artworks-list/list/list.component';

import {GalleriesListComponent} from './galleries/galleries-list/galleries-list.component';
import {FiltersComponent as GalleriesListFiltersComponent} from './galleries/galleries-list/filters/filters.component';
import {GridComponent as GalleriesListGridComponent} from './galleries/galleries-list/grid/grid.component';
import {ListComponent as GalleriesListListComponent} from './galleries/galleries-list/list/list.component';

import {ArtistsListComponent} from './artists/artists-list/artists-list.component';
import {FiltersComponent as ArtistsListFiltersComponent} from './artists/artists-list/filters/filters.component';
import {GridComponent as ArtistsListGridComponent} from './artists/artists-list/grid/grid.component';
import {ListComponent as ArtistsListListComponent} from './artists/artists-list/list/list.component';
import {ArtworksDetailComponent} from './artworks/artworks-detail/artworks-detail.component';
import {GalleriesDetailComponent} from './galleries/galleries-detail/galleries-detail.component';
import {ArtistsDetailComponent} from './artists/artists-detail/artists-detail.component';
import {StepOneComponent as RegisterStepOneComponent} from './auth/register/step-one/step-one.component';
import {StepTwoComponent as RegisterStepTwoComponent} from './auth/register/step-two/step-two.component';
import {NgxLoadingModule} from 'ngx-loading';
import {StepOneComponent as ResetStepOneComponent} from './auth/reset/step-one/step-one.component';
import {StepTwoComponent as ResetStepTwoComponent} from './auth/reset/step-two/step-two.component';

import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {OrderWindowComponent} from './common/components/order-window/order-window.component';

import {NavigationComponent as ProfileNavigationComponent} from "./profile/navigation/navigation.component";
import {ViewComponent as ProfileViewComponent} from "./profile/view/view.component";
import {EditComponent as ProfileEditComponent} from "./profile/edit/edit.component";
import {PaymentComponent as ProfilePaymentComponent} from "./profile/payment/payment.component";
import {WalletComponent as ProfileWalletComponent} from "./profile/wallet/wallet.component";
import {WishlistComponent as ProfileWishlistComponent} from "./profile/wishlist/wishlist.component";
import {NotificationsComponent as ProfileNotificationsComponent} from "./profile/notifications/notifications.component";
import {OrdersComponent as ProfileOrdersComponent} from './profile/orders/orders.component';
import {ArtworkStockComponent as ProfileArtworkStockComponent} from './profile/artwork-stock/artwork-stock.component';
import {DocumentsComponent as ProfileDocumentsComponent} from './profile/documents/documents.component';
import {DocumentAddComponent as ProfileDocumentAddComponent} from './profile/documents/add/document-add.component';
import {StorageComponent as ProfileStorageComponent} from './profile/storage/storage.component';
import {StorageAddComponent as ProfileStorageAddComponent} from './profile/storage/add/storage-add.component';
import {StorageEditComponent as ProfileStorageEditComponent} from './profile/storage/edit/storage-edit.component';
import { PaymentCardComponent } from './profile/payment/payment-card/payment-card.component';


export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    MainHeaderComponent,
    MainFooterComponent,
    MainPageComponent,
    HelpComponent,
    AboutComponent,
    ContactComponent,
    LoginComponent,
    RegisterComponent,
    ResetComponent,
    ArtworksListComponent,
    ArtworksListFiltersComponent,
    ArtworksListGridComponent,
    ArtworksListListComponent,
    GalleriesListComponent,
    GalleriesListFiltersComponent,
    GalleriesListGridComponent,
    GalleriesListListComponent,
    ArtistsListComponent,
    ArtistsListFiltersComponent,
    ArtistsListGridComponent,
    ArtistsListListComponent,
    ArtworksDetailComponent,
    GalleriesDetailComponent,
    ArtistsDetailComponent,
    RegisterStepOneComponent,
    RegisterStepTwoComponent,
    ResetStepOneComponent,
    ResetStepTwoComponent,
    PageNotFoundComponent,
    OrderWindowComponent,

    ProfileNavigationComponent,
    ProfileViewComponent,
    ProfileEditComponent,
    ProfilePaymentComponent,
    ProfileWalletComponent,
    ProfileWishlistComponent,
    ProfileNotificationsComponent,
    ProfileOrdersComponent,
    ProfileArtworkStockComponent,
    ProfileDocumentsComponent,
    ProfileDocumentAddComponent,
    ProfileStorageComponent,
    ProfileStorageAddComponent,
    ProfileStorageEditComponent,
    PaymentCardComponent,

  ],
  imports: [
    BrowserModule,
    CommonModule,
    YinyangCoreModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
    NgxLoadingModule.forRoot({}),
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgxPaginationModule,
    AdminModule
  ],
  providers:
    [Globals, FileService, AuthGuard, AdminGuard,
      {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
      {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}],
  bootstrap:
    [AppComponent]
})

export class AppModule {
}
