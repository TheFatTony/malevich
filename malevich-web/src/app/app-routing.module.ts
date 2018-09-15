import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";


import {MainPageComponent} from "./main/main-page/main-page.component";

const routes: Routes = [
  {path: '', redirectTo: '/main-page', pathMatch: 'full'},
  {path: 'main-page', component: MainPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
