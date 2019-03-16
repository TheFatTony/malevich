import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {environment} from "../../../../environments/environment.dev";
import {OrderDto} from "../../../_transfer/orderDto";
import {OrderWindowComponent} from "../../../common/components/order-window/order-window.component";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";

@Component({
  selector: 'app-artworks-list-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit {

  @ViewChild('myWindow') myWindow: OrderWindowComponent;

  @Input() artworkStocks;
  @Input() wishListMap: { [artworkStockId: number]: number } = {};
  @Input() artworkWalletMap: { [artworkStockId: number]: ArtworkStockDto } = {};

  @Output() onWishListToggle = new EventEmitter<ArtworkStockDto>();
  @Output() onOrderPlaced = new EventEmitter<OrderDto>();

  private url = environment.baseUrl;

  constructor(public translate: TranslateService) {
  }

  ngOnInit() {
  }

  openWindow(artworkStock: ArtworkStockDto, orderType: string) {
    this.myWindow.artworkStock(artworkStock);
    this.myWindow.orderType(orderType);
    this.myWindow.open();
  }

  isOwn(artworkStock: ArtworkStockDto) {
    if (!artworkStock)
      return false;

    return !!this.artworkWalletMap[artworkStock.id];
  }

  orderPlaced(order: OrderDto) {
    this.onOrderPlaced.next(order);
  }

  toggleWishList(artworkStock: ArtworkStockDto) {
    this.onWishListToggle.next(artworkStock);
  }

}
