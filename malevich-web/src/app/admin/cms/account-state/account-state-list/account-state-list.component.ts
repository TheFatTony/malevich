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

  constructor(private accountStateService: AccountStateService, public translate: TranslateService) {
    $.jqx.theme = 'metrodark';
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
