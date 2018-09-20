import {AfterViewInit, Component, OnInit} from '@angular/core';
import {CategoryService} from "../../../_services/category.service";
import {CategoryDto} from "../../../_transfer";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-artworks-artworks-list-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css']
})
export class FiltersComponent implements OnInit, AfterViewInit {

  categories: CategoryDto[];

  constructor(public translate: TranslateService, private categoryService: CategoryService) {
  }

  ngOnInit() {
    this.getCategories();
  }

  ngAfterViewInit(): void {
    $['HSCore'].helpers.HSRating.init();

    $['HSCore'].components.HSSlider.init('#rangeSlider1');
  }

  getCategories(): void {
    this.categoryService
      .getCategories()
      .subscribe(
        data => (this.categories = data)
      );
  }

}
