import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment.dev';
import {WishListDto} from '../_transfer/wishListDto';
import {BehaviorSubject} from "rxjs";
import {mergeMap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class WishListService {

  private url = environment.baseUrl + 'wishlist';

  private state = new BehaviorSubject<WishListDto[]>(null);

  constructor(private http: HttpClient) {
  }

  addToWishList(wishList: WishListDto) {
    return this.http.post<WishListDto>(this.url + '/addWish', wishList)
      .pipe(mergeMap(() => this.getWishListAll()));
  }

  getWishList(pageObj: any) {
    return this.http.post(this.url + '/list', pageObj, {observe: 'response'});
  }

  getWishListAll() {
    if (this.state.value)
      return this.state.asObservable();

    return this.http.get<WishListDto[]>(this.url + '/list_all')
      .pipe(mergeMap(data => {
        this.state.next(data);
        return this.state.asObservable();
      }));
  }

  removeWish(id: number) {
    return this.http.delete(this.url + '/remove/' + id)
      .pipe(mergeMap(() => this.getWishListAll()));
  }

}
