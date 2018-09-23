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
import {GalleriesListComponent} from "./galleries/galleries-list/galleries-list.component";
import {ArtistsListComponent} from "./artists/artists-list/artists-list.component";

const routes: Routes = [
  {path: '', redirectTo: '/main-page', pathMatch: 'full'},
  {path: 'main-page', component: MainPageComponent},
  {path: 'main/help', component: HelpComponent},
  {path: 'main/about', component: AboutComponent},
  {path: 'main/contact', component: ContactComponent},
  {path: 'auth/login', component: LoginComponent},
  {path: 'auth/register', component: RegisterComponent},
  {path: 'auth/reset', component: ResetComponent},
  {path: 'profile/trader', component: TraderProfileComponent},
  {path: 'artworks/artworks-list', component: ArtworksListComponent},
  {path: 'galleries/galleries-list', component: GalleriesListComponent},
  {path: 'artists/artists-list', component: ArtistsListComponent},
  {
    path: 'admin',
    component: AdminComponent,
    children: adminRoutes
    // ,canActivate: [AdminGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
