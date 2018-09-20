import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {CategoryDto} from "../_transfer";
import {map} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private url = environment.baseUrl + 'categories';

  constructor(private http: HttpClient) { }

  getCategories() {
    return this.http
      .get<CategoryDto[]>(this.url + '/list')
      .pipe(map(data => data));
  }

}
