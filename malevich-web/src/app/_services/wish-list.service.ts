import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment.dev';
import {WishListDto} from '../_transfer/wishListDto';
import {PageResponseDto} from "../_transfer/pageResponseDto";
import {PageSortableRequestDto} from "../_transfer/pageSortableRequestDto";

@Injectable({
  providedIn: 'root'
})
export class WishListService {

  private url = environment.baseUrl + 'wishlist';

  constructor(private http: HttpClient) {
  }

  addToWishList(wishList: WishListDto) {
    return this.http.post<WishListDto>(this.url + '/addWish', wishList);
  }

  getWishList(pageObj: PageSortableRequestDto) {
    return this.http.post<PageResponseDto<WishListDto>>(this.url + '/list', pageObj, {observe: 'response'});
  }

  getWishListAll() {
    return this.http.get<WishListDto[]>(this.url + '/list_all');
  }

  removeWish(id: number) {
    return this.http.delete(this.url + '/remove/' + id);
  }


}
