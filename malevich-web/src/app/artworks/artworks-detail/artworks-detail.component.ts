import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {environment} from '../../../environments/environment.dev';
import {TranslateService} from '@ngx-translate/core';
import {ActivatedRoute, Params} from '@angular/router';
import {OrderDto} from '../../_transfer/orderDto';
import {OrderService} from '../../_services/order.service';
import {ArtworkStockDto} from '../../_transfer/artworkStockDto';
import {ArtworkStockService} from '../../_services/artwork-stock.service';
import {TradeHistoryService} from '../../_services/trade-history.service';
import {TradeHistoryDto} from '../../_transfer/tradeHistoryDto';
import {jqxDropDownListComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxdropdownlist';
import {OrderPublicDto} from '../../_transfer/orderPublicDto';
import {OrderWindowComponent} from '../../common/components/order-window/order-window.component';
import {WishListService} from '../../_services/wish-list.service';
import {WishListDto} from '../../_transfer/wishListDto';
import {ParticipantService} from "../../_services/participant.service";
import {KycLevelService} from "../../_services/kyc-level.service";
import {Subject} from "rxjs";
import {Globals} from "../../globals";
import {map} from "rxjs/operators";

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

  instantPrice: number;
  lastPrice: number;

  placedOrders: OrderPublicDto[];

  tradeHistory: TradeHistoryDto[];

  tradingAccess: { read: boolean, write: boolean } = {read: false, write: false};

  public url = environment.baseUrl;

  constructor(private orderService: OrderService,
              private route: ActivatedRoute,
              public translate: TranslateService,
              private artworkStockService: ArtworkStockService,
              private tradeHistoryService: TradeHistoryService,
              private wishListService: WishListService,
              private participantService: ParticipantService,
              private kycLevelService: KycLevelService,
              private globals: Globals) {
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getArtworkStock();
    this.checkTradingAccess();
  }

  ngAfterViewInit(): void {
    $['HSCore'].helpers.HSRating.init();
    $['HSCore'].components.HSTabs.init('[role="tablist"]');
  }

  checkTradingAccess() {
    if (!this.globals.isAuthorised$) {
      const subj = new Subject();
      subj.next({read: false, write: false});
      return subj;
    }

    return this.participantService.getCurrent()
      .pipe(map(participant => {
        if (!participant)
          return {read: false, write: false};

        const level = participant.kycLevel.id;

        return {
          read: ['T_TIER0', 'T_TIER1', 'T_TIER2', 'G_TIER0', 'G_TIER1'].indexOf(level) >= 0,
          write: ['T_TIER1', 'T_TIER2', 'G_TIER1'].indexOf(level) >= 0
        }
      }))
      .subscribe(r => {
        this.tradingAccess = r;

        if (this.tradingAccess.read) {
          this.getOpenOrdersByArtworkId();
          this.getTradeHistoryByArtworkId();
        }
      });
  }

  getArtworkStock(): void {
    this.artworkStockService
      .getArtworkStock(this.id)
      .subscribe(
        data => (this.artworkStock = data)
      );
  }

  getOpenOrdersByArtworkId(): void {
    this.orderService
      .getOpenOrdersByArtworkId(this.id)
      .subscribe(
        data => {
          this.placedOrders = data.sort((a, b) => b.amount - a.amount);
          const ask = this.placedOrders.find(i => i.type.id == 'ASK');

          if (ask)
            this.instantPrice = ask.amount;
        }
      );
  }

  getTradeHistoryByArtworkId(): void {
    this.tradeHistoryService
      .findAllByArtworkId(this.id)
      .subscribe(
        data => {
          this.tradeHistory = data;
          const lastTrade = this.tradeHistory[0];
          if (lastTrade)
            this.lastPrice = lastTrade.amount;
        }
      );
  }

  openWindow() {
    this.myWindow.artworkStock(this.artworkStock);
    this.myWindow.open();
  }


  onOrderPlaced(order: OrderDto) {
    this.lastPrice = null;
    this.instantPrice = null;
    this.getOpenOrdersByArtworkId();
    this.getTradeHistoryByArtworkId();
  }

  addToWishList(): void {
    let wishList = new WishListDto();
    wishList.artworkStock = this.artworkStock;
    this.wishListService.addToWishList(wishList).subscribe();
  }

}
