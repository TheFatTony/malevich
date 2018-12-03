import {Component, OnInit, ViewChild} from '@angular/core';
import {OrderDto} from "../../../_transfer/orderDto";
import {OrderService} from "../../../_services/order.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {TradeTypeService} from "../../../_services/trade-type.service";
import {TradeTypeDto} from "../../../_transfer/tradeTypeDto";
import {environment} from "../../../../environments/environment.dev";

@Component({
  selector: 'app-profile-trader-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  orders: OrderDto[];
  selectedRowIndex: number = -1;
  tradeTypes: TradeTypeDto[];

  public url = environment.baseUrl;

  columns: any[] =
    [
      {datafield: 'Date', width: '20%', columntype: 'textbox'},
      {datafield: 'Amount', width: '15%', columntype: 'textbox'},
      {datafield: 'Artwork', width: '25%', columntype: 'textbox'},
      {datafield: 'Trade Type', width: '15%', columntype: 'textbox'},
      {datafield: 'Type', width: '5%', columntype: 'textbox'},
      {datafield: 'Best Bid', width: '10%', columntype: 'textbox'},
      {datafield: 'Current Ask', width: '10%', columntype: 'textbox'}
    ];

  constructor(private orderService: OrderService,
              public translate: TranslateService,
              private tradeTypeService: TradeTypeService) {
  }

  ngOnInit() {
    this.getPlacedOrders();
    this.getTradeTypes();
  }

  ngAfterViewInit(): void {
  }

  getPlacedOrders(): void {
    this.orderService
      .getPlacedOrders()
      .subscribe(
        data => {
          this.orders = data;
        }
      );
  }

  getTradeTypes(): void {
    this.tradeTypeService
      .getTradeTypes()
      .subscribe(
        data => (this.tradeTypes = data)
      );
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  onCancelOrderButton() {
    if (this.selectedRowIndex < 0)
      return;
    let order = this.orders.splice(this.selectedRowIndex, 1)[0];
    this.orderService.cancel(order).subscribe();
  }
}
