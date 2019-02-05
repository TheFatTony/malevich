import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {AccountStateDto} from "../../../_transfer/accountStateDto";
import {AccountStateService} from "../../../_services/account-state.service";

@Component({
  selector: 'app-wallets',
  templateUrl: './wallets.component.html',
  styleUrls: ['./wallets.component.css']
})
export class WalletsComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;

  wallets: AccountStateDto[];

  selectedRowIndex: number = -1;


  constructor(private accountStateService: AccountStateService,
              private router: Router,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getCommissions();
  }

  getCommissions(): void {
    this.accountStateService
      .getAllWallets()
      .subscribe(
        data => (this.wallets = data)
      );
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  onAddButton() {
    // this.router.navigate(['/admin/cms/commissions/edit'], {
    //   queryParams: {
    //     "new": true
    //   }
    // });
  }

  onEditButton() {
    if (this.selectedRowIndex < 0)
      return;

    // let artist = this.commissions[this.selectedRowIndex];
    //
    // this.router.navigate(['/admin/cms/commissions/edit'], {
    //   queryParams: {
    //     "id": artist.id
    //   }
    // });
  }

}
