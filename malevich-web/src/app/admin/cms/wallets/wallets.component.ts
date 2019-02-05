import {Component, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {AccountStateDto} from "../../../_transfer/accountStateDto";
import {AccountStateService} from "../../../_services/account-state.service";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {PaymentsDto} from "../../../_transfer/paymentsDto";
import {PaymentsService} from "../../../_services/payments.service";

@Component({
  selector: 'app-wallets',
  templateUrl: './wallets.component.html',
  styleUrls: ['./wallets.component.css']
})
export class WalletsComponent implements OnInit, OnDestroy {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('withdrawWindow') withdrawWindow: jqxWindowComponent;

  x: number;
  y: number;

  wallets: AccountStateDto[];
  public newPayment: PaymentsDto;
  public newWithdraw: PaymentsDto;
  public selectedWallet: AccountStateDto;
  payments: PaymentsDto[];

  selectedRowIndex: number = -1;


  constructor(private accountStateService: AccountStateService,
              private paymentsService: PaymentsService,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getWallets();
  }

  ngOnDestroy(): void {
    this.myWindow.close();
    this.withdrawWindow.close();
  }

  getWallets(): void {
    this.accountStateService
      .getAllWallets()
      .subscribe(
        data => (this.wallets = data)
      );
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
    this.selectedWallet = this.wallets[this.selectedRowIndex];
  }

  sendPayment() {
    this.paymentsService.insert(this.newPayment).subscribe(() => {
      this.myWindow.close();
      this.getWallets();
    });
  }

  sendWithdraw() {
    this.paymentsService.insert(this.newWithdraw).subscribe(() => {
      this.withdrawWindow.close();
      this.getWallets();
    });
  }

  openPaymentWindow() {
    this.newPayment = new PaymentsDto();
    this.newPayment.participant = this.selectedWallet.participant;
    this.myWindow.width(310);
    this.myWindow.height(220);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  openWithdrawWindow() {
    this.newWithdraw = new PaymentsDto();
    this.newWithdraw.participant = this.selectedWallet.participant;
    this.withdrawWindow.width(310);
    this.withdrawWindow.height(220);
    this.withdrawWindow.open();
    this.withdrawWindow.move(this.x, this.y);
  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }



}
