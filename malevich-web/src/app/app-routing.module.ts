import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';


import {MainPageComponent} from './main/main-page/main-page.component';
import {AdminGuard} from './_guards/admin.guard';

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
import {AuthGuard} from './_guards/auth.guard';

import {ViewComponent as GalleryProfileSecurityViewComponent} from './profile/gallery-profile/view/view.component';
import {EditComponent as GalleryProfileSecurityEditComponent} from './profile/gallery-profile/edit/edit.component';
import {ArtworkStockComponent as GalleryProfileArtworkStockComponent} from './profile/gallery-profile/artwork-stock/artwork-stock.component';
import {OrdersComponent as GalleryProfileOrdersComponent} from './profile/gallery-profile/orders/orders.component';
import {DocumentsComponent as GalleryProfileDocumentsComponent} from './profile/gallery-profile/documents/documents.component';
import {PaymentComponent} from './profile/trader-profile/payment/payment.component';
import {WalletComponent} from './profile/trader-profile/wallet/wallet.component';
import {WishlistComponent} from './profile/trader-profile/wishlist/wishlist.component';
import {NotificationsComponent} from './profile/trader-profile/notifications/notifications.component';
import {ViewComponent as GalleryProfileNotificationsComponent} from './profile/gallery-profile/notifications/view/view.component';
import {OrdersComponent as TraderProfileOrdersComponent} from './profile/trader-profile/orders/orders.component';
import {DocumentsComponent as TraderProfileDocumentsComponent} from './profile/trader-profile/documents/documents.component';
import {ArtworkStockComponent as TraderProfileArtworkStockComponent} from './profile/trader-profile/artwork-stock/artwork-stock.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {StorageAddComponent} from './profile/gallery-profile/storage/add/storage-add.component';
import {StorageEditComponent} from './profile/gallery-profile/storage/edit/storage-edit.component';
import {StorageComponent} from './profile/gallery-profile/storage/storage.component';
import {DocumentAddComponent as GalleryDocumentAddComponent} from './profile/gallery-profile/documents/add/document-add.component';
import {DocumentAddComponent as TraderDocumentAddComponent} from './profile/trader-profile/documents/add/document-add.component';
import {ViewComponent as ProfileViewComponent} from "./profile/view/view.component";
import {EditComponent as ProfileEditComponent} from "./profile/edit/edit.component";
import {PaymentComponent as ProfilePaymentComponent} from "./profile/payment/payment.component";
import {WalletComponent as ProfileWalletComponent} from "./profile/wallet/wallet.component";

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
    path: 'profile', canActivate: [AuthGuard], children: [
      {path: 'view', component: ProfileViewComponent, canActivate: [AuthGuard]},
      {path: 'edit', component: ProfileEditComponent, canActivate: [AuthGuard]},
      {path: 'payment', component: ProfilePaymentComponent, canActivate: [AuthGuard]},
      {path: 'wallet', component: ProfileWalletComponent, canActivate: [AuthGuard]},
      // {path: 'wishlist', component: WishlistComponent, canActivate: [AuthGuard]},
      // {path: 'notifications', component: NotificationsComponent, canActivate: [AuthGuard]},
      // {path: 'orders/list', component: TraderProfileOrdersComponent, canActivate: [AuthGuard]},
      // {path: 'artworkstock', component: TraderProfileArtworkStockComponent, canActivate: [AuthGuard]},
      // {path: 'documents/list/trader', component: TraderProfileDocumentsComponent, canActivate: [AuthGuard]},
      // {path: 'documents/add', component: TraderDocumentAddComponent, canActivate: [AuthGuard]}
    ]
  },

  // {
  //   path: 'profile/trader', canActivate: [AuthGuard], children: [
  //   {path: 'view', component: TraderProfileSecurityViewComponent, canActivate: [AuthGuard]},
  //   {path: 'edit', component: TraderProfileSecurityEditComponent, canActivate: [AuthGuard]},
  //   {path: 'payment', component: PaymentComponent, canActivate: [AuthGuard]},
  //   {path: 'wallet', component: WalletComponent, canActivate: [AuthGuard]},
  //   {path: 'wishlist', component: WishlistComponent, canActivate: [AuthGuard]},
  //   {path: 'notifications', component: NotificationsComponent, canActivate: [AuthGuard]},
  //   {path: 'orders/list', component: TraderProfileOrdersComponent, canActivate: [AuthGuard]},
  //   {path: 'artworkstock', component: TraderProfileArtworkStockComponent, canActivate: [AuthGuard]},
  //   {path: 'documents/list/trader', component: TraderProfileDocumentsComponent, canActivate: [AuthGuard]},
  //   {path: 'documents/add', component: TraderDocumentAddComponent, canActivate: [AuthGuard]}
  // ]
  // },

  {
    path: 'profile/gallery', canActivate: [AuthGuard], children: [
    {path: 'view', component: GalleryProfileSecurityViewComponent, canActivate: [AuthGuard]},
    {path: 'edit', component: GalleryProfileSecurityEditComponent, canActivate: [AuthGuard]},
    {path: 'notifications', component: GalleryProfileNotificationsComponent, canActivate: [AuthGuard]},
    {path: 'orders/list', component: GalleryProfileOrdersComponent, canActivate: [AuthGuard]},
    {path: 'artworks/list', component: GalleryProfileArtworkStockComponent, canActivate: [AuthGuard]},
    {path: 'storage', component: StorageComponent, canActivate: [AuthGuard]},
    {path: 'storage/add', component: StorageAddComponent, canActivate: [AuthGuard]},
    {path: 'storage/edit/:id', component: StorageEditComponent, canActivate: [AuthGuard]},
    {path: 'documents/list/gallery', component: GalleryProfileDocumentsComponent, canActivate: [AuthGuard]},
    {path: 'documents/add', component: GalleryDocumentAddComponent, canActivate: [AuthGuard]}
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
