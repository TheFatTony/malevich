import {AfterViewInit, Component, EventEmitter, OnInit, Output} from '@angular/core';
import {CategoryService} from '../../../_services/category.service';
import {CategoryDto} from '../../../_transfer';
import {TranslateService} from '@ngx-translate/core';
import {FilterDto} from '../../../_transfer/filterDto';
import {ArtworksListComponent} from '../artworks-list.component';

@Component({
  selector: 'app-artworks-list-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css']
})
export class FiltersComponent implements OnInit, AfterViewInit {
  @Output() onCategoryChange: EventEmitter<any> = new EventEmitter<any>();
  categories: CategoryDto[];
  filterDto: FilterDto;
  size: number = 9;
  page: number = 0;
  sort: string = '';

  constructor(public translate: TranslateService,
              private categoryService: CategoryService,
              private artworksListComponent: ArtworksListComponent) {
  }

  ngOnInit() {
    this.filterDto = new FilterDto();
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

  filter(id: number): void {
    this.filterDto.page = this.page;
    this.filterDto.size = this.size;
    this.filterDto.sort = this.sort;
    this.filterDto.categoryId = id;
    this.onCategoryChange.emit(this.filterDto);
    this.artworksListComponent.stocksByFilter(this.filterDto);
  }

}
