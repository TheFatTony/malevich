import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PageResponseDto} from "../../../_transfer/pageResponseDto";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-artworks-list-view',
  templateUrl: './artworks-view.component.html',
  styleUrls: ['./artworks-view.component.css']
})
export class ArtworksViewComponent implements OnInit {

  @Output() onPageSizeChanged = new EventEmitter<number>();
  @Output() onSortChanged = new EventEmitter<string>();
  @Output() onPageChanged = new EventEmitter<number>();


  @Input('artworkStockPage')
  set stockDataSetter(value: PageResponseDto<ArtworkStockDto>) {
    if(!value) return;
    this.stockData = value;
  }

  showGrid: boolean = true;
  stockData: PageResponseDto<ArtworkStockDto> = new PageResponseDto();

  constructor(public translate: TranslateService) {
  }

  ngOnInit() {
  }

  setPageSize(pageSize: number) {
    this.onPageSizeChanged.next(pageSize);
  }

  setSort(sort: string) {
    this.onSortChanged.next(sort);
  }

  setPage(page: number) {
    this.onPageChanged.next(page);
  }


}
