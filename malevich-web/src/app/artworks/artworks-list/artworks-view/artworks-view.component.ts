import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PageResponseDto} from "../../../_transfer/pageResponseDto";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {TranslateService} from "@ngx-translate/core";
import {WishListService} from "../../../_services/wish-list.service";
import {WishListDto} from "../../../_transfer/wishListDto";
import {ArtworkStockService} from "../../../_services/artwork-stock.service";
import {OrderDto} from "../../../_transfer/orderDto";

@Component({
  selector: 'app-artworks-list-view',
  templateUrl: './artworks-view.component.html',
  styleUrls: ['./artworks-view.component.css']
})
export class ArtworksViewComponent implements OnInit {

  @Output() onPageSizeChanged = new EventEmitter<number>();
  @Output() onSortChanged = new EventEmitter<string>();
  @Output() onPageChanged = new EventEmitter<number>();
  @Output() onWishListToggle = new EventEmitter<ArtworkStockDto>();
  @Output() onOrderPlaced = new EventEmitter<OrderDto>();

  @Input('artworkStockPage')
  set stockDataSetter(value: PageResponseDto<ArtworkStockDto>) {
    if (!value) return;
    this.stockData = value;
  }

  showGrid: boolean = true;
  stockData: PageResponseDto<ArtworkStockDto> = new PageResponseDto();
  wishListMap: { [artworkStockId: number]: number } = {};
  ownArtworksMap: { [artworkStockId: number]: ArtworkStockDto } = {};

  constructor(public translate: TranslateService,
              private wishListService: WishListService,
              private artworkStockService: ArtworkStockService) {
  }

  ngOnInit() {
    this.getWishList();
    this.getOwnArtworks();
  }

  setPageSize(pageSize: number) {
    this.onPageSizeChanged.next(pageSize);
  }

  setSort(sort: string) {
    this.onSortChanged.next(sort);
  }

  setPage(page: number) {
    this.onPageChanged.next(page);
  }

  getOwnArtworks() {
    this.artworkStockService.getOwnArtworks().subscribe(data => {
      if (!data) return;

      const newMap = {};
      for (let artworkStock of data) {
        newMap[artworkStock.id] = artworkStock;
      }

      this.ownArtworksMap = newMap;
    });
  }

  getWishList() {
    this.wishListService.getWishListAll().subscribe(data => {
      if (!data) return;

      const newMap = {};
      for (let wish of data) {
        newMap[wish.artworkStock.id] = wish.id;
      }

      this.wishListMap = newMap;
    });
  }

  toggleWishList(artworkStock: ArtworkStockDto) {
    const wishId = this.wishListMap[artworkStock.id];
    if (wishId != null)
      this.wishListService.removeWish(wishId).subscribe(() => {
        this.getWishList();
        this.onWishListToggle.next(artworkStock);
      });
    else {
      const newWish = new WishListDto();
      newWish.artworkStock = artworkStock;

      this.wishListService.addToWishList(newWish).subscribe(() => {
        this.getWishList();
        this.onWishListToggle.next(artworkStock);
      });
    }
  }

  orderPlaced(order: OrderDto) {
    this.getOwnArtworks();
    this.onOrderPlaced.next(order);
  }
}
