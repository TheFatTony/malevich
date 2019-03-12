import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PageResponseDto} from "../../../_transfer/pageResponseDto";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {TranslateService} from "@ngx-translate/core";
import {WishListService} from "../../../_services/wish-list.service";
import {WishListDto} from "../../../_transfer/wishListDto";

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


  @Input('artworkStockPage')
  set stockDataSetter(value: PageResponseDto<ArtworkStockDto>) {
    if (!value) return;
    this.stockData = value;
  }

  showGrid: boolean = true;
  stockData: PageResponseDto<ArtworkStockDto> = new PageResponseDto();
  wishListMap: { [artworkStockId: number]: number } = {};

  constructor(public translate: TranslateService,
              private wishListService: WishListService) {
  }

  ngOnInit() {
    this.getWishList();
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
}
