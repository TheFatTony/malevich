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

@Component({
  selector: 'app-artworks-detail',
  templateUrl: './artworks-detail.component.html',
  styleUrls: ['./artworks-detail.component.css']
})
export class ArtworksDetailComponent implements OnInit, AfterViewInit {

  @ViewChild('myWindow') myWindow: jqxWindowComponent;

  artworkStock: ArtworkStockDto;
  id: number;
  public newOrder: OrderDto;

  placedOrders: OrderDto[];


  tradeHistory: TradeHistoryDto[];

  x: number;
  y: number;

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
    this.findAllByArtworkId();
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

  findAllByArtworkId(): void {
    this.tradeHistoryService
      .findAllByArtworkId(this.id)
      .subscribe(
        data => (this.tradeHistory = data)
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
      this.findAllByArtworkId();
    });
  }

}
