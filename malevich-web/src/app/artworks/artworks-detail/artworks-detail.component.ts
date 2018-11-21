import {AfterViewInit, Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {environment} from "../../../environments/environment.dev";
import {TranslateService} from "@ngx-translate/core";
import {ActivatedRoute, Params} from "@angular/router";
import {OrderDto} from "../../_transfer/orderDto";
import {OrderService} from "../../_services/order.service";
import {ArtworkStockDto} from "../../_transfer/artworkStockDto";
import {ArtworkStockService} from "../../_services/artwork-stock.service";
import {TradeHistoryService} from "../../_services/trade-history.service";
import {TradeHistoryDto} from "../../_transfer/tradeHistoryDto";
import {jqxDropDownListComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxdropdownlist";
import {OrderPublicDto} from "../../_transfer/orderPublicDto";
import {OrderWindowComponent} from "../../common/components/order-window/order-window.component";

@Component({
  selector: 'app-artworks-detail',
  templateUrl: './artworks-detail.component.html',
  styleUrls: ['./artworks-detail.component.css']
})
export class ArtworksDetailComponent implements OnInit, AfterViewInit {

  @ViewChild('myWindow') myWindow: OrderWindowComponent;
  @ViewChild('tradeTypeDropDown') tradeTypeDropDown: jqxDropDownListComponent;

  artworkStock: ArtworkStockDto;
  id: number;

  placedOrders: OrderPublicDto[];

  tradeHistory: TradeHistoryDto[];

  public url = environment.baseUrl;

  constructor(private orderService: OrderService,
              private route: ActivatedRoute,
              public translate: TranslateService,
              private artworkStockService: ArtworkStockService,
              private tradeHistoryService: TradeHistoryService) {
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getArtworkStock();
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

  openWindow() {
    this.myWindow.artworkStock(this.artworkStock);
    this.myWindow.open();
  }


  onOrderPlaced(order: OrderDto) {
    this.getOrdersByArtworkId();
    this.getTradeHistoryByArtworkId();
  }

}
