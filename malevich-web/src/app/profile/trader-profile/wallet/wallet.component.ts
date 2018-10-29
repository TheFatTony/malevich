import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {PaymentsDto} from "../../../_transfer/paymentsDto";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {PaymentsService} from "../../../_services/payments.service";

@Component({
  selector: 'app-profile-trader-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {

  @ViewChild('myWindow') myWindow: jqxWindowComponent;

  public newPayment: PaymentsDto;

  x: number;
  y: number;

  constructor(private paymentsService: PaymentsService) {
    $.jqx.theme = 'malevich';
  }

  ngOnInit() {
  }

  sendButton() {
    console.info(this.newPayment);
    this.paymentsService.insert(this.newPayment).subscribe(() => {
      this.myWindow.close();
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

}
