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

  public newPayment: PaymentsDto;
  public accountState: AccountStateDto;

  x: number;
  y: number;

  constructor(private paymentsService: PaymentsService, private accountStateService: AccountStateService) {
    $.jqx.theme = 'malevich';
  }

  ngOnInit() {
    this.getTraderAccountState();
  }

  sendButton() {
    console.info(this.newPayment);
    this.paymentsService.insert(this.newPayment).subscribe(() => {
      this.myWindow.close();
      this.getTraderAccountState();
    });
  }

  openWindow() {
    this.newPayment = new PaymentsDto();
    this.myWindow.width(310);
    this.myWindow.height(220);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

  getTraderAccountState(): void {
    this.accountStateService
      .getTraderAccountState()
      .subscribe(
        data => (this.accountState = data)
      );
  }

}
