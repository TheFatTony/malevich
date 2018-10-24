import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {UserListComponent} from './user/user-list/user-list.component';
import {FilesListComponent} from './files/files-list/files-list.component';
// import {AdminComponent} from './admin.component';
import { TransactionsListComponent } from './cms/transactions/transactions-list/transactions-list.component';
import { AccountStateListComponent } from './cms/account-state/account-state-list/account-state-list.component';



export const routes: Routes = [
  {path: '', redirectTo: '/admin/dashboard', pathMatch: 'full'},
  // {path: '', component: AdminComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'user/user-list', component: UserListComponent},
  {path: 'cms/files/list', component: FilesListComponent},
  {path: 'cms/transactions/list', component: TransactionsListComponent},
  {path: 'cms/account-state/list', component: AccountStateListComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
