import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {environment} from "../../../../environments/environment.dev";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {OrderDto} from "../../../_transfer/orderDto";
import {WishListDto} from "../../../_transfer/wishListDto";
import {OrderWindowComponent} from "../../../common/components/order-window/order-window.component";
import {WishListService} from "../../../_services/wish-list.service";

@Component({
  selector: 'app-artworks-list-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  @ViewChild('myWindow') myWindow: OrderWindowComponent;

  @Input() artworkStocks;
  @Input() wishListMap: { [artworkStockId: number]: number } = {};

  @Output() onWishListToggle = new EventEmitter<ArtworkStockDto>();

  private url = environment.baseUrl;

  constructor(public translate: TranslateService) {
  }

  ngOnInit() {
  }

  openWindow(artworkStock: ArtworkStockDto) {
    this.myWindow.artworkStock(artworkStock);
    this.myWindow.open();
  }

  onOrderPlaced(order: OrderDto) {

  }

  toggleWishList(artworkStock: ArtworkStockDto){
    this.onWishListToggle.next(artworkStock);
  }
}
