import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {OrderDto} from "../../../_transfer/orderDto";
import {OrderService} from "../../../_services/order.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {jqxWindowComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow';
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {ArtworkStockService} from "../../../_services/artwork-stock.service";
import {TradeTypeService} from "../../../_services/trade-type.service";
import {TradeTypeDto} from "../../../_transfer/tradeTypeDto";
import {OrderTypeService} from "../../../_services/order-type.service";

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
      {text: 'Date', datafield: 'Date', columntype: 'textbox', width: '20%'},
      {text: 'Amount', datafield: 'Amount', columntype: 'textbox', width: '20%'},
      {text: 'Artwork', datafield: 'Artwork', columntype: 'textbox', width: '20%'},
      {text: 'Trade Type', datafield: 'Trade Type', columntype: 'textbox', width: '20%'},
      {text: 'Type', datafield: 'Type', columntype: 'textbox', width: '20%'}
    ];

  orders: OrderDto[];
  public newOrder: OrderDto;

  artworkStocks: ArtworkStockDto[];

  tradeTypes: TradeTypeDto[];

  x: number;
  y: number;

  constructor(private artworkStockService: ArtworkStockService,
              private orderService: OrderService,
              public translateService: TranslateService,
              private tradeTypeService: TradeTypeService,
              private orderTypeService: OrderTypeService) {
    $.jqx.theme = 'malevich';
  }

  ngOnInit() {
    this.getPlacedOrders();
    this.getArtworkStock();
    this.getTradeTypes();
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

  getArtworkStock(): void {
    this.artworkStockService
      .getArtworkStock()
      .subscribe(
        data => (this.artworkStocks = data)
      );
  }

  getTradeTypes(): void {
    this.tradeTypeService
      .getTradeTypes()
      .subscribe(
        data => (this.tradeTypes = data)
      );
  }

  openWindow() {
    this.newOrder = new OrderDto();
    this.newOrder.artworkStock = new ArtworkStockDto();
    this.newOrder.tradeType = new TradeTypeDto();
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  sendButton() {
    console.info(this.newOrder);
    // @ts-ignore
    this.newOrder.artworkStock = this.artworkStocks[this.newOrder.artworkStock];
    // @ts-ignore
    this.newOrder.tradeType = this.tradeTypes[this.newOrder.tradeType];
    console.info(this.newOrder);

    this.orderService.insertOrder(this.newOrder).subscribe();

  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

}
