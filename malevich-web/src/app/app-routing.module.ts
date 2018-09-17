import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";


import {MainPageComponent} from "./main/main-page/main-page.component";
import {AdminGuard} from "./_guards/admin.guard";

import {routes as adminRoutes} from './admin/admin-routing.module';
import {AdminComponent} from "./admin/admin.component";

const routes: Routes = [
  {path: '', redirectTo: '/main-page', pathMatch: 'full'},
  {path: 'main-page', component: MainPageComponent},
  {
    path: 'admin',
    component: AdminComponent,
    children: adminRoutes,
    canActivate: [AdminGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
