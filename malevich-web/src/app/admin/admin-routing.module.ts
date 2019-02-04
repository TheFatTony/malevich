import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {UserListComponent} from './user/user-list/user-list.component';
import {FilesListComponent} from './files/files-list/files-list.component';
// import {AdminComponent} from './admin.component';
import {TransactionsListComponent} from './cms/transactions/transactions-list/transactions-list.component';
import {AccountStateListComponent} from './cms/account-state/account-state-list/account-state-list.component';
import {OrdersListComponent} from './cms/orders/orders-list/orders-list.component';
import {HelpCategoryComponent} from './cms/help/help-category/help-category.component';
import {HelpTopicComponent} from './cms/help/help-topic/help-topic.component';
import {DelayedChangeComponent} from "./cms/delayed-change/delayed-change.component";
import {ArtistListComponent} from "./cms/artists/list/artist-list.component";
import {ArtistEditComponent} from "./cms/artists/edit/artist-edit.component";
import {CommissionRuleListComponent} from "./cms/commissions/list/commission-rule-list.component";
import {CommissionRuleEditComponent} from "./cms/commissions/edit/commission-rule-edit.component";
import {ExchangeOrdersComponent} from "./cms/exchange-orders/exchange-orders.component";


export const routes: Routes = [
  {path: '', redirectTo: '/admin/dashboard', pathMatch: 'full'},
  // {path: '', component: AdminComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'user/user-list', component: UserListComponent},
  {path: 'cms/files/list', component: FilesListComponent},
  {path: 'cms/transactions/list', component: TransactionsListComponent},
  {path: 'cms/account-state/list', component: AccountStateListComponent},
  {path: 'cms/orders/list', component: OrdersListComponent},
  {path: 'cms/help-category/categoryList', component: HelpCategoryComponent},
  {path: 'cms/help-topic/topicList', component: HelpTopicComponent},
  {path: 'cms/delayed-changes', component: DelayedChangeComponent},
  {path: 'cms/artists', component: ArtistListComponent},
  {path: 'cms/artists/edit', component: ArtistEditComponent},
  {path: 'cms/commissions', component: CommissionRuleListComponent},
  {path: 'cms/commissions/edit', component: CommissionRuleEditComponent},
  {path: 'cms/exchange-orders', component: ExchangeOrdersComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
