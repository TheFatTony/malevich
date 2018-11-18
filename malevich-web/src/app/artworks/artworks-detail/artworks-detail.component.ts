import {AfterViewInit, Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {environment} from "../../../environments/environment.dev";
import {TranslateService} from "@ngx-translate/core";
import {ActivatedRoute, Params} from "@angular/router";
import {OrderDto} from "../../_transfer/orderDto";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {OrderService} from "../../_services/order.service";
import {ArtworkStockDto} from "../../_transfer/artworkStockDto";
import {ArtworkStockService} from "../../_services/artwork-stock.service";
import {TradeHistoryService} from "../../_services/trade-history.service";
import {TradeHistoryDto} from "../../_transfer/tradeHistoryDto";
import {TradeTypeService} from "../../_services/trade-type.service";
import {TradeTypeDto} from "../../_transfer/tradeTypeDto";
import {jqxDropDownListComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxdropdownlist";
import {OrderPublicDto} from "../../_transfer/orderPublicDto";

@Component({
  selector: 'app-artworks-detail',
  templateUrl: './artworks-detail.component.html',
  styleUrls: ['./artworks-detail.component.css']
})
export class ArtworksDetailComponent implements OnInit, AfterViewInit {

  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('tradeTypeDropDown') tradeTypeDropDown: jqxDropDownListComponent;

  artworkStock: ArtworkStockDto;
  id: number;
  public newOrder: OrderDto;

  placedOrders: OrderPublicDto[];

  tradeHistory: TradeHistoryDto[];

  x: number;
  y: number;

  expirationDateInputHidden: boolean = true;

  public url = environment.baseUrl;
  private tradeTypes: any[];

  constructor(private orderService: OrderService,
              private route: ActivatedRoute,
              public translate: TranslateService,
              private artworkStockService: ArtworkStockService,
              private tradeHistoryService: TradeHistoryService,
              private tradeTypeService:TradeTypeService) {
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getArtworkStock();
    this.getTradeTypes();
    this.getOrdersByArtworkId();
    this.getTradeHistoryByArtworkId();
  }

  ngAfterViewInit(): void {
    $['HSCore'].helpers.HSRating.init();
    $['HSCore'].components.HSTabs.init('[role="tablist"]');
  }

  getArtworkStock(): void {
    this.artworkStockService
      .getArtworkStock(this.id)
      .subscribe(
        data => (this.artworkStock = data)
      );
  }

  getOrdersByArtworkId(): void {
    this.orderService
      .getOrdersByArtworkId(this.id)
      .subscribe(
        data => (this.placedOrders = data)
      );
  }

  getTradeHistoryByArtworkId(): void {
    this.tradeHistoryService
      .findAllByArtworkId(this.id)
      .subscribe(
        data => (this.tradeHistory = data)
      );
  }

  getTradeTypes(): void {
    this.tradeTypeService
      .getTradeTypes()
      .subscribe(
        data => {
          this.tradeTypes = data.map(t => ({
            title: t.nameMl[this.translate.currentLang],
            value: t
          }));
        }
      );
  }

  openWindow() {
    this.newOrder = new OrderDto();
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

  sendButton() {
    this.myWindow.close();
    this.newOrder.artworkStock = this.artworkStock;
    this.orderService.placeBid(this.newOrder).subscribe(() => {
      this.getOrdersByArtworkId();
      this.getTradeHistoryByArtworkId();
    });
  }

  showExpirationDateInput(show: boolean){
    this.expirationDateInputHidden = !show;
  }

  setTradeType(value:TradeTypeDto){
    this.newOrder.tradeType = value;

    switch (value.id) {
      case "GTC_":{
        this.newOrder.expirationDate = null;
        this.showExpirationDateInput(false);
        break;
      }
      case "GTT0":{
        this.newOrder.expirationDate = new Date();
        this.showExpirationDateInput(false);
        break;
      }
      case "GTD_":{
        this.showExpirationDateInput(true);
        break;
      }
    }
  }

  onMyWindowOpen() {
    this.tradeTypeDropDown.selectIndex(0);
  }

}
