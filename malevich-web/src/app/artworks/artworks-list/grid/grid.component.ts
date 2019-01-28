import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {environment} from "../../../../environments/environment.dev";
import {OrderDto} from "../../../_transfer/orderDto";
import {WishListDto} from "../../../_transfer/wishListDto";
import {OrderWindowComponent} from "../../../common/components/order-window/order-window.component";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {WishListService} from "../../../_services/wish-list.service";

@Component({
  selector: 'app-artworks-list-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit {

  @ViewChild('myWindow') myWindow: OrderWindowComponent;

  @Input() artworkStocks;

  private url = environment.baseUrl;

  constructor(public translate: TranslateService,
              private wishListService: WishListService) {
  }

  ngOnInit() {
  }

  openWindow(artworkStock: ArtworkStockDto) {
    this.myWindow.artworkStock(artworkStock);
    this.myWindow.open();
  }


  onOrderPlaced(order: OrderDto) {

  }

  addToWishList(artworkStock: ArtworkStockDto): void {
    let wishList = new WishListDto();
    wishList.artworkStock = artworkStock;
    this.wishListService.addToWishList(wishList).subscribe();
  }

}
