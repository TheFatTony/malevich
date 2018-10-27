import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";


import {MainPageComponent} from "./main/main-page/main-page.component";
import {AdminGuard} from "./_guards/admin.guard";

import {routes as adminRoutes} from './admin/admin-routing.module';
import {AdminComponent} from "./admin/admin.component";
import {HelpComponent} from "./main/help/help.component";
import {AboutComponent} from "./main/about/about.component";
import {ContactComponent} from "./main/contact/contact.component";
import {LoginComponent} from "./auth/login/login.component";
import {RegisterComponent} from "./auth/register/register.component";
import {ResetComponent} from "./auth/reset/reset.component";
import {ArtworksListComponent} from "./artworks/artworks-list/artworks-list.component";
import {ArtworksDetailComponent} from "./artworks/artworks-detail/artworks-detail.component";
import {GalleriesListComponent} from "./galleries/galleries-list/galleries-list.component";
import {GalleriesDetailComponent} from "./galleries/galleries-detail/galleries-detail.component";
import {ArtistsListComponent} from "./artists/artists-list/artists-list.component";
import {ArtistsDetailComponent} from "./artists/artists-detail/artists-detail.component";
import {AuthGuard} from "./_guards/auth.guard";

import {ViewComponent as GalleryProfileSecurityViewComponent} from './profile/gallery-profile/view/view.component';
import {EditComponent as GalleryProfileSecurityEditComponent} from './profile/gallery-profile/edit/edit.component';
import {ArtworkStockComponent as GalleryProfileArtworkStockComponent} from './profile/gallery-profile/artwork-stock/artwork-stock.component';
import {ViewComponent as TraderProfileSecurityViewComponent} from "./profile/trader-profile/view/view.component";
import {EditComponent as TraderProfileSecurityEditComponent} from "./profile/trader-profile/edit/edit.component";
import { OrdersComponent as GalleryProfileOrdersComponent} from './profile/gallery-profile/orders/orders.component';
import {PaymentComponent} from "./profile/trader-profile/payment/payment.component";
import {WalletComponent} from "./profile/trader-profile/wallet/wallet.component";
import {WishlistComponent} from "./profile/trader-profile/wishlist/wishlist.component";
import {NotificationsComponent} from "./profile/trader-profile/notifications/notifications.component";
import {ViewComponent as GalleryProfileNotificationsComponent} from './profile/gallery-profile/notifications/view/view.component';
import {AddComponent as GalleryProfileArtworkStockAddComponent} from "./profile/gallery-profile/artwork-stock/add/add.component";
import {EditComponent as GalleryProfileArtworkStockEditComponent} from "./profile/gallery-profile/artwork-stock/edit/edit.component";

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
    path: 'profile/trader', canActivate: [AuthGuard], children: [
      {path: 'view', component: TraderProfileSecurityViewComponent, canActivate: [AuthGuard]},
      {path: 'edit', component: TraderProfileSecurityEditComponent, canActivate: [AuthGuard]},
      {path: 'payment', component: PaymentComponent, canActivate: [AuthGuard]},
      {path: 'wallet', component: WalletComponent, canActivate: [AuthGuard]},
      {path: 'wishlist', component: WishlistComponent, canActivate: [AuthGuard]},
      {path: 'notifications', component: NotificationsComponent, canActivate: [AuthGuard]},
    ]
  },

  {
    path: 'profile/gallery', canActivate: [AuthGuard], children: [
      {path: 'view', component: GalleryProfileSecurityViewComponent, canActivate: [AuthGuard]},
      {path: 'edit', component: GalleryProfileSecurityEditComponent, canActivate: [AuthGuard]},
      {path: 'notifications', component: GalleryProfileNotificationsComponent, canActivate: [AuthGuard]},
      {path: 'orders/list', component: GalleryProfileOrdersComponent, canActivate: [AuthGuard]},
      {path: 'artworkstok', component: GalleryProfileArtworkStockComponent, canActivate: [AuthGuard]},
      {path: 'artworkstok/add', component: GalleryProfileArtworkStockAddComponent, canActivate: [AuthGuard]},
      {path: 'artworkstok/edit/:id', component: GalleryProfileArtworkStockEditComponent, canActivate: [AuthGuard]}
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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
