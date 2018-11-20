import {Component, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment.dev';
import {TranslateService} from '@ngx-translate/core';
import {ArtworkStockService} from '../../_services/artwork-stock.service';
import {ArtworkStockDto} from '../../_transfer/artworkStockDto';
import {PageSortableRequestDto} from '../../_transfer/pageSortableRequestDto';
import {FilterDto} from '../../_transfer/filterDto';

@Component({
  selector: 'app-artworks-list',
  templateUrl: './artworks-list.component.html',
  styleUrls: ['./artworks-list.component.css']
})
export class ArtworksListComponent implements OnInit {
  showGrid: boolean = true;
  artworkStocks: ArtworkStockDto[];
  pageSortable: PageSortableRequestDto;
  filterDto: FilterDto;
  stockData: any = {};

  private url = environment.baseUrl;

  constructor(public translate: TranslateService, private artworkStockService: ArtworkStockService) {
    this.pageSortable = new PageSortableRequestDto();
    this.filterDto = new FilterDto();
  }

  ngOnInit() {
    this.filterDto.page = 0;
    this.filterDto.size = 2;
    this.stocksByFilter(this.filterDto);
  }

  ngAfterViewInit(): void {
  }

  public holdDataFromFilter(filterDto: any): void {
    this.filterDto = filterDto;
  }

  getArtworkStock(): void {
    this.artworkStockService
      .getArtworkStocks()
      .subscribe(
        data => (this.artworkStocks = data)
      );
  }

  setPage(page: number) {
    this.filterDto.page = page;
    this.stocksByFilter(this.filterDto);
  }

  setSize(size: number) {
    this.filterDto.size = size;
    this.stocksByFilter(this.filterDto);
  }

  stocksByFilter(filterDtoObj: any) {
    this.artworkStockService.stocksByFilter(filterDtoObj).subscribe(
      (data) => {
        this.stockData = data.body;
        this.stockData.currentPage = filterDtoObj.page + 1;
        this.stockData.size = filterDtoObj.size;
      }
    );
  }
}

