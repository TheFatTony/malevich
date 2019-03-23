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
import {forkJoin, Subject} from "rxjs";
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
  isOwnArtwork: boolean;
  wishListItem: WishListDto;
  ownOrder: OrderPublicDto;
  id: number;

  orderDefaultAmount = 0;

  placeOrderMode: boolean = false;

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

  get placeOrderButtonCaption() {
    if (!this.ownOrder) {
      return this.isOwnArtwork ? 'MAIN.ARTWORK.PLACE_ASK' : 'MAIN.ARTWORK.PLACE_BID';
    } else {
      return this.isOwnArtwork ? 'MAIN.ARTWORK.REPLACE_ASK' : 'MAIN.ARTWORK.REPLACE_BID';
    }
  }

  checkTradingAccess() {
    if (!this.globals.isAuthorised$.getValue()) {
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
    this.isOwnArtwork = null;
    this.wishListItem = null;

    forkJoin(
      this.artworkStockService.getArtworkStock(this.id),
      this.artworkStockService.getOwnArtworks(),
      this.wishListService.getWishListAll()
    ).pipe(map(([artwork, ownedArtworks, wishList]) => {
      this.artworkStock = artwork;
      const owned = ownedArtworks.find(a => a.id == artwork.id);

      this.isOwnArtwork = !!owned;

      this.wishListItem = wishList.find(i => i.artworkStock.id == artwork.id);

    })).subscribe();
  }

  getOpenOrdersByArtworkId(): void {
    this.ownOrder = null;

    this.orderService
      .getOpenOrdersByArtworkId(this.id)
      .subscribe(
        data => {
          this.placedOrders = data.sort((a, b) => b.amount - a.amount);

          for (let order of this.placedOrders) {
            if (order.isOwn)
              this.ownOrder = order;
          }
        }
      );
  }

  getTradeHistoryByArtworkId(): void {
    this.tradeHistoryService
      .findAllByArtworkId(this.id)
      .subscribe(
        data => {
          // console.log(data[0]);
          this.tradeHistory = data.sort((x, y) => new Date(y.effectiveDate).getTime() - new Date(x.effectiveDate).getTime());
          //
        }
      );
  }

  placeOrder() {
    this.orderDefaultAmount = 0;
    this.placeOrderMode = true;
  }

  onOrderPlaced(order: OrderDto) {
    this.placeOrderMode = false;
    this.getArtworkStock();
    this.getOpenOrdersByArtworkId();
    this.getTradeHistoryByArtworkId();
  }

  wishListClick(): void {
    if (this.wishListItem) {
      this.wishListService.removeWish(this.wishListItem.id).subscribe(() => {
        this.getArtworkStock();
      });
    } else {
      let wishList = new WishListDto();
      wishList.artworkStock = this.artworkStock;
      this.wishListService.addToWishList(wishList).subscribe(() => {
        this.getArtworkStock();
      });
    }
  }

  onOrderCanceled() {
    this.placeOrderMode = false;
  }

  instantPriceClick() {
    this.orderDefaultAmount = this.artworkStock.instantPrice | 0;
    this.placeOrderMode = true;
  }

  ownOrderClick() {
    const cancelOrder = new OrderDto();
    cancelOrder.id = this.ownOrder.id;
    cancelOrder.amount = this.ownOrder.amount;
    cancelOrder.artworkStock = this.ownOrder.artworkStock;
    cancelOrder.type = this.ownOrder.type;

    this.orderService.cancel(cancelOrder).subscribe(() => {
      this.getOpenOrdersByArtworkId();
    });
  }

  getDescriptionHtml(text: string) {
    text = text.replace('\n', '</p><p>');
    text = '<p>' + text + '</p>';
    return text;
  }
}
