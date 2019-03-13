import {Component, OnInit} from '@angular/core';
import {WishListService} from '../../_services/wish-list.service';
import {PageSortableRequestDto} from '../../_transfer/pageSortableRequestDto';
import {PageResponseDto} from "../../_transfer/pageResponseDto";
import {ArtworkStockDto} from "../../_transfer/artworkStockDto";

@Component({
  selector: 'app-profile-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {

  wishList: PageResponseDto<ArtworkStockDto>;
  pageSortable: PageSortableRequestDto;

  constructor(private wishListService: WishListService) {
  }

  ngOnInit() {
    this.pageSortable = new PageSortableRequestDto();
    this.pageSortable.page = 0;
    this.pageSortable.size = 3;
    this.pageSortable.sort = '';
    this.getWishList(this.pageSortable);
  }

  setPage(page: number) {
    this.pageSortable.page = page;
    this.getWishList(this.pageSortable);
  }

  setSort(sort: string) {
    this.pageSortable.sort = sort;
    this.getWishList(this.pageSortable);
  }

  setPageSize(size: number) {
    this.pageSortable.size = size;
    this.getWishList(this.pageSortable);
  }

  getWishList(pageObj: PageSortableRequestDto) {
    return this.wishListService.getWishList(pageObj).subscribe(data => {
      this.wishList = new PageResponseDto();
      Object.assign(this.wishList, data.body);
      this.wishList.data = data.body.data.map(w => w.artworkStock);
    });
  }

  toggleWishList() {
    this.getWishList(this.pageSortable);
  }

}
