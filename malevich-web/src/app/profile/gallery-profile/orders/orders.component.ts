import {Component, OnInit, ViewChild} from '@angular/core';
import {OrderDto} from "../../../_transfer/orderDto";
import {OrderService} from "../../../_services/order.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {jqxWindowComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow';

@Component({
  selector: 'app-profile-gallery-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('myWindow') myWindow: jqxWindowComponent;

  public columns =
    [
      { text: 'Date', datafield: 'Date', columntype: 'textbox', width: '20%' },
      { text: 'Amount', datafield: 'Amount',columntype: 'textbox', width: '20%' },
      { text: 'Artwork', datafield: 'Artwork',columntype: 'textbox', width: '20%' },
      { text: 'Trade Type', datafield: 'Trade Type',columntype: 'textbox', width: '20%' },
      { text: 'Type', datafield: 'Type',columntype: 'textbox', width: '20%' }
    ];

  orders: OrderDto[];
  newOrder: OrderDto;

  constructor(private orderService: OrderService, public translateService: TranslateService) {
    $.jqx.theme = 'malevich';
  }

  ngOnInit() {
    this.getPlacedOrders();
  }

  ngAfterViewInit(): void {
  }

  getPlacedOrders(): void {
    this.orderService
      .getPlacedOrders()
      .subscribe(
        data => (this.orders = data)
      );
  }

  openWindow() {
    this.newOrder = new OrderDto();
    this.myWindow.open();
    this.myWindow.move(460, 260);
  }

  sendButton() {

  }
}
