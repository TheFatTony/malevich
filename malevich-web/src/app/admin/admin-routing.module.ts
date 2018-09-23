import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {UserListComponent} from './user/user-list/user-list.component';
import {FilesListComponent} from './files/files-list/files-list.component';
// import {AdminComponent} from './admin.component';



export const routes: Routes = [
  {path: '', redirectTo: '/admin/dashboard', pathMatch: 'full'},
  // {path: '', component: AdminComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'user/user-list', component: UserListComponent},
  {path: 'files/list', component: FilesListComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
