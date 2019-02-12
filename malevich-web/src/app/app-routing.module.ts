import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';


import {MainPageComponent} from './main/main-page/main-page.component';

import {routes as adminRoutes} from './admin/admin-routing.module';
import {AdminComponent} from './admin/admin.component';
import {HelpComponent} from './main/help/help.component';
import {AboutComponent} from './main/about/about.component';
import {ContactComponent} from './main/contact/contact.component';
import {LoginComponent} from './auth/login/login.component';
import {RegisterComponent} from './auth/register/register.component';
import {ResetComponent} from './auth/reset/reset.component';
import {ArtworksListComponent} from './artworks/artworks-list/artworks-list.component';
import {ArtworksDetailComponent} from './artworks/artworks-detail/artworks-detail.component';
import {GalleriesListComponent} from './galleries/galleries-list/galleries-list.component';
import {GalleriesDetailComponent} from './galleries/galleries-detail/galleries-detail.component';
import {ArtistsListComponent} from './artists/artists-list/artists-list.component';
import {ArtistsDetailComponent} from './artists/artists-detail/artists-detail.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';

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
import {AdminGuard, AuthGuard} from '../../node_modules/yinyang-core';
import {KycGuard} from "./_guards/kyc.guard";


const routes: Routes = [
  {path: '', redirectTo: '/main-page', pathMatch: 'full'},
  {path: 'main-page', component: MainPageComponent},
  {path: 'main/help', component: HelpComponent},
  {path: 'main/about', component: AboutComponent},
  {path: 'main/contact', component: ContactComponent},
  {path: 'auth/login', component: LoginComponent},
  {path: 'auth/register', component: RegisterComponent},
  {path: 'auth/reset', component: ResetComponent},

  {
    path: 'profile', canActivate: [AuthGuard], canActivateChild: [KycGuard], children: [
      {path: 'view', component: ProfileViewComponent, canActivate: [AuthGuard]},
      {path: 'edit', component: ProfileEditComponent, canActivate: [AuthGuard]},
      {path: 'payment', component: ProfilePaymentComponent, data: {kycLevels: ['T_TIER1', 'G_TIER1']}, canActivate: [AuthGuard]},
      {path: 'wallet', component: ProfileWalletComponent, data: {kycLevels: ['T_TIER1', 'G_TIER1']}, canActivate: [AuthGuard]},
      {path: 'wishlist', component: ProfileWishlistComponent, canActivate: [AuthGuard]},
      {path: 'notifications', component: ProfileNotificationsComponent, canActivate: [AuthGuard]},
      {path: 'orders', component: ProfileOrdersComponent, data: {kycLevels: ['T_TIER2', 'G_TIER1']}, canActivate: [AuthGuard]},
      {path: 'artworks', component: ProfileArtworkStockComponent, data: {kycLevels: ['T_TIER2', 'G_TIER1']}, canActivate: [AuthGuard]},
      {path: 'documents', component: ProfileDocumentsComponent, canActivate: [AuthGuard]},
      {path: 'documents/add', component: ProfileDocumentAddComponent, canActivate: [AuthGuard]},
      {path: 'storage', component: ProfileStorageComponent, data: {kycLevels: ['G_TIER1']}, canActivate: [AuthGuard]},
      {path: 'storage/add', component: ProfileStorageAddComponent, data: {kycLevels: ['G_TIER1']}, canActivate: [AuthGuard]},
      {path: 'storage/edit/:id', component: ProfileStorageEditComponent, data: {kycLevels: ['G_TIER1']}, canActivate: [AuthGuard]},
    ]
  },

  {path: 'artworks/artworks-list', component: ArtworksListComponent},
  {path: 'artworks/artworks-detail/:id', component: ArtworksDetailComponent},
  {path: 'galleries/galleries-list', component: GalleriesListComponent},
  {path: 'galleries/galleries-detail/:id', component: GalleriesDetailComponent},
  {path: 'artists/artists-list', component: ArtistsListComponent},
  {path: 'artists/artists-detail/:id', component: ArtistsDetailComponent},
  {
    path: 'admin', component: AdminComponent, children: adminRoutes, canActivate: [AdminGuard]
  },
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
