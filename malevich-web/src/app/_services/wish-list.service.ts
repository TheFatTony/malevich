import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment.dev';
import {WishListDto} from '../_transfer/wishListDto';

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

  getWishList(pageObj: any) {
    return this.http.post(this.url + '/list', pageObj, {observe: 'response'});
  }

  removeWish(id: number) {
    return this.http.delete(this.url + '/remove/' + id);
  }

}
