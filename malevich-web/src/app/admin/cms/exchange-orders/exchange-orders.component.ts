import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {TranslateService} from "@ngx-translate/core";
import {ExchangeOrderDto} from "../../../_transfer/exchangeOrderDto";
import {ExchangeOrderService} from "../../../_services/exchange-order.service";

@Component({
  selector: 'app-exchange-orders',
  templateUrl: './exchange-orders.component.html',
  styleUrls: ['./exchange-orders.component.css']
})
export class ExchangeOrdersComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('viewWindow') viewWindow: jqxWindowComponent;
  @ViewChild('declineWindow') declineWindow: jqxWindowComponent;


  exchangeOrders: ExchangeOrderDto[];
  exchangeOrder: ExchangeOrderDto;

  private x: number;
  private y: number;

  comment: string;

  columns: any[] =
    [
      {datafield: 'Date', width: '20%', columntype: 'textbox'},
      {datafield: 'Exchange Name', width: '20%', columntype: 'textbox'},
      {datafield: 'Currency Pair', width: '20%', columntype: 'textbox'},
      {datafield: 'Amount', width: '20%', columntype: 'textbox'},
      {datafield: 'Type', width: '10%', columntype: 'textbox'},
      {datafield: 'Status', width: '10%', columntype: 'textbox'}
    ];

  constructor(private exchangeOrderService: ExchangeOrderService, public translate: TranslateService) {
  }

  ngOnInit() {
    this.getExchangeOrders();
  }

  getExchangeOrders(): void {
    this.exchangeOrderService
      .getExchangeOrders()
      .subscribe(
        data => (this.exchangeOrders = data)
      );
  }

  // sendButton(): void {
  //   this.exchangeOrder.comment = this.comment;
  //   this.exchangeOrderService.declineChange(this.exchangeOrder).subscribe(() => {
  //     this.getDelayedChanges();
  //     this.declineWindow.close();
  //   });
  // }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

}
