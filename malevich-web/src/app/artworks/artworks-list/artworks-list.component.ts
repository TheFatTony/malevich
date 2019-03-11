import {Component, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment.dev';
import {TranslateService} from '@ngx-translate/core';
import {ArtworkStockService} from '../../_services/artwork-stock.service';
import {ArtworkStockDto} from '../../_transfer/artworkStockDto';
import {PageSortableRequestDto} from '../../_transfer/pageSortableRequestDto';
import {FilterDto} from '../../_transfer/filterDto';
import {PageResponseDto} from "../../_transfer/pageResponseDto";

@Component({
  selector: 'app-artworks-list',
  templateUrl: './artworks-list.component.html',
  styleUrls: ['./artworks-list.component.css']
})
export class ArtworksListComponent implements OnInit {
  pageSortable: PageSortableRequestDto;
  filterDto: FilterDto;
  stockData: PageResponseDto<ArtworkStockDto>;
  pager: any = {};
  private url = environment.baseUrl;

  constructor(public translate: TranslateService,
              private artworkStockService: ArtworkStockService) {
    this.pageSortable = new PageSortableRequestDto();
    this.filterDto = new FilterDto();
  }

  ngOnInit() {
    this.filterDto.page = 0;
    this.filterDto.size = 9;
    this.filterDto.sort = '';
    this.stocksByFilter(this.filterDto);
  }

  ngAfterViewInit(): void {
  }

  public holdDataFromFilter(filterDto: any): void {
    this.filterDto = filterDto;
  }

  setPage(page: number) {
    this.filterDto.page = page;
    this.stocksByFilter(this.filterDto);
  }

  setSort(sort: string) {
    this.filterDto.sort = sort;
    this.stocksByFilter(this.filterDto);
  }

  setPageSize(size: number) {
    this.filterDto.size = size;
    this.stocksByFilter(this.filterDto);
  }

  stocksByFilter(filterDtoObj: any) {
    this.artworkStockService.stocksByFilter(filterDtoObj).subscribe(
      (data) => {
        this.stockData = data.body;
      }
    );
  }
}

