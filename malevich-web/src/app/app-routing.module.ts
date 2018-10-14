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
import {TraderProfileComponent} from "./profile/trader-profile/trader-profile.component";
import {ArtworksListComponent} from "./artworks/artworks-list/artworks-list.component";
import {ArtworksDetailComponent} from "./artworks/artworks-detail/artworks-detail.component";
import {GalleriesListComponent} from "./galleries/galleries-list/galleries-list.component";
import {GalleriesDetailComponent} from "./galleries/galleries-detail/galleries-detail.component";
import {ArtistsListComponent} from "./artists/artists-list/artists-list.component";
import {ArtistsDetailComponent} from "./artists/artists-detail/artists-detail.component";
import {AuthGuard} from "./_guards/auth.guard";

import {ViewComponent as GalleryProfileSecurityView} from './profile/gallery-profile/view/view.component';
import {EditComponent as GalleryProfileSecurityEdit} from './profile/gallery-profile/edit/edit.component';
import { ViewComponent as GalleryProfileNotificationsComponent} from './profile/gallery-profile/notifications/view/view.component';

const routes: Routes = [
  {path: '', redirectTo: '/main-page', pathMatch: 'full'},
  {path: 'main-page', component: MainPageComponent},
  {path: 'main/help', component: HelpComponent},
  {path: 'main/about', component: AboutComponent},
  {path: 'main/contact', component: ContactComponent},
  {path: 'auth/login', component: LoginComponent},
  {path: 'auth/register', component: RegisterComponent},
  {path: 'auth/reset', component: ResetComponent},

  {path: 'profile/trader/security', redirectTo: 'profile/trader', canActivate: [AuthGuard]},
  {path: 'profile/trader', component: TraderProfileComponent, canActivate: [AuthGuard]},
  {path: 'profile/trader/:view', component: TraderProfileComponent, canActivate: [AuthGuard]},

  {
    path: 'profile/gallery', canActivate: [AuthGuard], children: [
      {path: 'view', component: GalleryProfileSecurityView, canActivate: [AuthGuard]},
      {path: 'edit', component: GalleryProfileSecurityEdit, canActivate: [AuthGuard]},
      {path: 'notifications', component: GalleryProfileNotificationsComponent, canActivate: [AuthGuard]}
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
