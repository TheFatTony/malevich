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
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-profile-gallery-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('myWindow') myWindow: jqxWindowComponent;

  // public columns =
  //   [
  //     {text: 'Date', datafield: 'Date', columntype: 'textbox', width: '20%'},
  //     {text: 'Amount', datafield: 'Amount', columntype: 'textbox', width: '20%'},
  //     {text: 'Artwork', datafield: 'Artwork', columntype: 'textbox', width: '20%'},
  //     {text: 'Trade Type', datafield: 'Trade Type', columntype: 'textbox', width: '20%'},
  //     {text: 'Type', datafield: 'Type', columntype: 'textbox', width: '20%'}
  //   ];

  orders: OrderDto[];
  public newOrder: OrderDto;

  artworkStocks: ArtworkStockDto[];

  tradeTypes: TradeTypeDto[];

  x: number;
  y: number;

  public url = environment.baseUrl;

  constructor(private artworkStockService: ArtworkStockService,
              private orderService: OrderService,
              public translateService: TranslateService,
              private tradeTypeService: TradeTypeService,
              private orderTypeService: OrderTypeService) {
    $.jqx.theme = 'malevich';
  }


  renderer = (index: number, label: string, value: any): string => {
    let datarecord = this.artworkStocks[index];
    let table = '<table style="min-width: 50px;"><tr><td style="width: 100px;" rowspan="2">' +
      '<img class="img-fluid" src="https://via.placeholder.com/50x50/img8.jpg" alt="Image Description">' +
      '</td><td>' + '<span class="d-block g-color-gray-dark-v4">'+ datarecord.artwork.titleMl[this.translateService.currentLang] + '</span>' + '</td></tr><tr><td>' +
      '<span class="d-block g-color-lightred">'+datarecord.artwork.category.categoryNameMl[this.translateService.currentLang]+'</span>' + '</td></tr></table>';
    return table;
  };

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
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  sendButton() {
    console.info(this.newOrder);
    // @ts-ignore
    this.newOrder.artworkStock = this.artworkStocks[this.newOrder.artworkStock]
    // @ts-ignore
    this.newOrder.tradeType = this.tradeTypes[this.newOrder.tradeType];
    console.info(this.newOrder);

    this.myWindow.close();
    this.orderService.insertOrder(this.newOrder).subscribe(() => {this.getPlacedOrders();});

  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

}
