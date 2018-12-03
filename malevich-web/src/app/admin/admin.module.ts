import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MainMenuComponent} from './main-menu/main-menu.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {UserListComponent} from './user/user-list/user-list.component';
import {AdminComponent} from './admin.component';
import {AdminRoutingModule} from './admin-routing.module';
import {FilesListComponent} from './files/files-list/files-list.component';
import {UsersService} from './_services/users.service';
import {TransactionsListComponent} from './cms/transactions/transactions-list/transactions-list.component';
import {AccountStateListComponent} from './cms/account-state/account-state-list/account-state-list.component';
import {OrdersListComponent} from './cms/orders/orders-list/orders-list.component';
import {HelpCategoryComponent} from './cms/help/help-category/help-category.component';
import {HelpTopicComponent} from './cms/help/help-topic/help-topic.component';
import {DelayedChangeComponent} from './cms/delayed-change/delayed-change.component';
import {NgxJsonViewerModule} from "ngx-json-viewer";
import {YinyangCoreModule} from '../../../node_modules/yinyang-core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    AdminRoutingModule,
    YinyangCoreModule,
    ReactiveFormsModule,
    FormsModule,
    NgxJsonViewerModule
  ],
  declarations: [
    AdminComponent,
    MainMenuComponent,
    DashboardComponent,
    UserListComponent,
    FilesListComponent,
    TransactionsListComponent,
    AccountStateListComponent,
    OrdersListComponent,
    HelpCategoryComponent,
    HelpTopicComponent,
    DelayedChangeComponent],
  providers: [UsersService]
})
export class AdminModule {
}
