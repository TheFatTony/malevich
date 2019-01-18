import {Component, OnInit, ViewChild} from '@angular/core';
import {OrderDto} from '../../_transfer/orderDto';
import {OrderService} from '../../_services/order.service';
import {TranslateService} from '@ngx-translate/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {TradeTypeService} from '../../_services/trade-type.service';
import {TradeTypeDto} from '../../_transfer/tradeTypeDto';
import {environment} from '../../../environments/environment.dev';

@Component({
  selector: 'app-profile-orders',
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
      {datafield: this.translate.instant('PROFILE.GRID.DATE'), width: '20%', columntype: 'textbox'},
      {datafield: this.translate.instant('PROFILE.GRID.AMOUNT'), width: '15%', columntype: 'textbox'},
      {datafield: this.translate.instant('PROFILE.GRID.ARTWORK'), width: '25%', columntype: 'textbox'},
      {datafield: this.translate.instant('PROFILE.GRID.TRADER_TYPE'), width: '15%', columntype: 'textbox'},
      {datafield: this.translate.instant('PROFILE.GRID.TYPE'), width: '5%', columntype: 'textbox'},
      {datafield: this.translate.instant('PROFILE.GRID.BEST_BID'), width: '10%', columntype: 'textbox'},
      {datafield: this.translate.instant('PROFILE.GRID.CURRENT_ASK'), width: '10%', columntype: 'textbox'}
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
