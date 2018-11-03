import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.dev";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkStockService} from "../../_services/artwork-stock.service";
import {ArtworkStockDto} from "../../_transfer/artworkStockDto";

@Component({
  selector: 'app-artworks-list',
  templateUrl: './artworks-list.component.html',
  styleUrls: ['./artworks-list.component.css']
})
export class ArtworksListComponent implements OnInit {

  showGrid: boolean = true;
  artworkStocks: ArtworkStockDto[];

  private url = environment.baseUrl;

  constructor(public translate: TranslateService, private artworkStockService: ArtworkStockService) {
  }

  ngOnInit() {
    this.getArtworkStock();
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

}
