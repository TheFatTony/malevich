import {Component, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment.dev';
import {TranslateService} from '@ngx-translate/core';
import {ArtworkStockService} from '../../_services/artwork-stock.service';
import {ArtworkStockDto} from '../../_transfer/artworkStockDto';
import {PageSortableRequestDto} from '../../_transfer/pageSortableRequestDto';

@Component({
  selector: 'app-artworks-list',
  templateUrl: './artworks-list.component.html',
  styleUrls: ['./artworks-list.component.css']
})
export class ArtworksListComponent implements OnInit {

  showGrid: boolean = true;
  artworkStocks: ArtworkStockDto[];
  pageSortable: PageSortableRequestDto;
  stockData: any = {};
  currentPage: number = 0;
  size: number = 2;
  private url = environment.baseUrl;

  constructor(public translate: TranslateService, private artworkStockService: ArtworkStockService) {
    this.pageSortable = new PageSortableRequestDto();
  }

  ngOnInit() {
    this.getArtworkStockPagination(this.currentPage, this.size);
  }

  ngAfterViewInit(): void {
  }

  getArtworkStock(): void {
    this.artworkStockService
      .getArtworkStocks()
      .subscribe(
        data => (this.artworkStocks = data)
      );
  }

  getArtworkStockPagination(page: number, size: number) {

    this.artworkStockService.getArtworkStocksPagination({page: page, size: size}).subscribe(
      (data) => {
        this.stockData = data.body;
        this.stockData.currentPage = page + 1;
        this.stockData.size = size;
      }
    );
  }
}

