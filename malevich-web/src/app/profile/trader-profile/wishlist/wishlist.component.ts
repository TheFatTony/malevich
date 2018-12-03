import {Component, OnInit} from '@angular/core';
import {WishListService} from '../../../_services/wish-list.service';
import {TranslateService} from '@ngx-translate/core';
import {PageSortableRequestDto} from '../../../_transfer/pageSortableRequestDto';
import {Router} from '@angular/router';

@Component({
  selector: 'app-profile-trader-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {

  wishList: any = {};
  pageSortable: PageSortableRequestDto;

  constructor(private wishListService: WishListService, private translate: TranslateService, private router: Router) {
  }

  ngOnInit() {
    this.pageSortable = new PageSortableRequestDto();
    this.pageSortable.page = 0;
    this.pageSortable.size = 3;
    this.getWishList(this.pageSortable);
  }

  setPage(page: number) {
    this.pageSortable.page = page;
    this.getWishList(this.pageSortable);
  }

  getWishList(pageObj: any) {
    return this.wishListService.getWishList(pageObj).subscribe(data => {
      this.wishList = data.body;
      this.wishList.currentPage = pageObj.page + 1;
      this.wishList.size = pageObj.size;
    });
  }

  removeWish(id: number, currentPage: number) {
    return this.wishListService.removeWish(id).subscribe(data => {
      this.pageSortable.page = currentPage;
      this.getWishList(this.pageSortable);
    });
  }

}
