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

  columns(names: any): any[] {
    return [
      {dataField: 'DATE', text: names['PROFILE.GRID.DATE'], width: '20%', columntype: 'textbox'},
      {dataField: 'AMOUNT', text: names['PROFILE.GRID.AMOUNT'], width: '15%', columntype: 'textbox'},
      {dataField: 'ARTWORK', text: names['PROFILE.GRID.ARTWORK'], width: '25%', columntype: 'textbox'},
      {dataField: 'TRADER_TYPE', text: names['PROFILE.GRID.TRADER_TYPE'], width: '15%', columntype: 'textbox'},
      {dataField: 'TYPE', text: names['PROFILE.GRID.TYPE'], width: '5%', columntype: 'textbox'},
      {dataField: 'BEST_BID', text: names['PROFILE.GRID.BEST_BID'], width: '10%', columntype: 'textbox'},
      {dataField: 'CURRENT_ASK', text: names['PROFILE.GRID.CURRENT_ASK'], width: '10%', columntype: 'textbox'}
    ];
  }

  constructor(private orderService: OrderService,
              public translate: TranslateService,
              private tradeTypeService: TradeTypeService) {
    this.updateGrid();
  }

  ngOnInit() {
    this.getPlacedOrders();
    this.getTradeTypes();
  }

  ngAfterViewInit(): void {
  }

  updateGrid() {
    this.translate
      .get([
        'PROFILE.GRID.DATE',
        'PROFILE.GRID.AMOUNT',
        'PROFILE.GRID.ARTWORK',
        'PROFILE.GRID.TRADER_TYPE',
        'PROFILE.GRID.TYPE',
        'PROFILE.GRID.BEST_BID',
        'PROFILE.GRID.CURRENT_ASK'
      ])
      .subscribe(data => {
        this.myGrid.hideloadelement();
        this.myGrid.beginupdate();
        this.myGrid.setOptions
        ({
          columns: this.columns(data)
        });
        this.myGrid.endupdate();
      });
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
