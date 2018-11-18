import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {TranslateService} from "@ngx-translate/core";
import {AccountStateDto} from "../../../../_transfer/accountStateDto";
import {AccountStateService} from "../../../../_services/account-state.service";

@Component({
  selector: 'app-account-state-list',
  templateUrl: './account-state-list.component.html',
  styleUrls: ['./account-state-list.component.css']
})
export class AccountStateListComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  accountStates: AccountStateDto[];

  columns: any[] =
    [
      {datafield: 'Countreparty', width: '25%', columntype: 'textbox'},
      {datafield: 'Artwork', width: '25%', columntype: 'textbox'},
      {datafield: 'Amount', width: '25%', columntype: 'number'},
      {datafield: 'Quantity', width: '25%', columntype: 'number'}
    ];

  constructor(private accountStateService: AccountStateService, public translate: TranslateService) {
    }

  getAccountState(): void {
    this.accountStateService
      .getAccountStates()
      .subscribe(
        data => (this.accountStates = data)
      );
  }

  ngOnInit() {
    this.getAccountState();
  }

}
