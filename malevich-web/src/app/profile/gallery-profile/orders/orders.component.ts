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
import {jqxValidatorComponent} from '../../../../../node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxvalidator';

@Component({
  selector: 'app-profile-gallery-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('myValidator') myValidator: jqxValidatorComponent;

  orders: OrderDto[];
  public newOrder: OrderDto;

  artworkStocks: ArtworkStockDto[];

  tradeTypes: TradeTypeDto[];

  x: number;
  y: number;

  public url = environment.baseUrl;

  public artworkStocksComboBox: any[];

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

  rules =
    [
      {
        input: '.artworkStockInput',
        message: 'Field is required!',
        action: 'keyup, blur',
        rule: (input: any, commit: any): any => {
          if (this.newOrder) {
            if (this.newOrder.artworkStock !== null) {
              return true;
            }
          }
          return false;
        }
      },
      {
        input: '.amountInput',
        message: 'Field is required!',
        action: 'keyup, blur',
        rule: (input: any, commit: any): any => {
          if (this.newOrder) {
            if (this.newOrder.amount != null) {
              return true;
            }
          }
          return false;
        }
      }
    ];

  constructor(private artworkStockService: ArtworkStockService,
              private orderService: OrderService,
              public translate: TranslateService,
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
      .getArtworkStocks()
      .subscribe(
        data => {
          this.artworkStocks = data;
          this.artworkStocksComboBox = data.map(artworkStock => ({
              id: artworkStock,
              title: artworkStock.artwork.titleMl[this.translate.currentLang],
              html: '<table style="min-width: 50px;"><tr><td style="width: 50px; height: 50px;" rowspan="2">' +
                '<img class="img-fluid" src="https://via.placeholder.com/50x50/img8.jpg">' +
                '</td><td>' + '<span class="d-block g-color-gray-dark-v4">' + artworkStock.artwork.titleMl[this.translate.currentLang] + '</span>' + '</td></tr><tr><td>' +
                '<span class="d-block g-color-lightred">' + artworkStock.artwork.category.categoryNameMl[this.translate.currentLang] + '</span>' + '</td></tr></table>'
            })
          );
        });
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
    this.myWindow.width(310);
    this.myWindow.height(220);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  sendButton() {
    this.myValidator.validate();
  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

  ValidationSuccess($event: any) {
    this.myWindow.close();
    this.orderService.placeAsk(this.newOrder).subscribe(() => {
      this.getPlacedOrders();
    });
  }
}
