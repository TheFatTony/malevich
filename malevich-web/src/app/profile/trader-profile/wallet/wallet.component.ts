import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {PaymentsDto} from "../../../_transfer/paymentsDto";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {PaymentsService} from "../../../_services/payments.service";
import {AccountStateService} from "../../../_services/account-state.service";
import {AccountStateDto} from "../../../_transfer/accountStateDto";

@Component({
  selector: 'app-profile-trader-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {

  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('withdrawWindow') withdrawWindow: jqxWindowComponent;

  public newPayment: PaymentsDto;
  public newWithdraw: PaymentsDto;
  public accountState: AccountStateDto;

  x: number;
  y: number;

  constructor(private paymentsService: PaymentsService, private accountStateService: AccountStateService) {
    }

  ngOnInit() {
    this.getTraderAccountState();
  }

  sendPayment() {
    this.paymentsService.insert(this.newPayment).subscribe(() => {
      this.myWindow.close();
      this.getTraderAccountState();
    });
  }

  sendWithdraw() {
    this.paymentsService.insert(this.newWithdraw).subscribe(() => {
      this.withdrawWindow.close();
      this.getTraderAccountState();
    });
  }

  openPaymentWindow() {
    this.newPayment = new PaymentsDto();
    this.myWindow.width(310);
    this.myWindow.height(220);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  openWithdrawWindow() {
    this.newWithdraw = new PaymentsDto();
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

  getTraderAccountState(): void {
    this.accountStateService
      .getTraderWallet()
      .subscribe(
        data => (this.accountState = data)
      );
  }

}
