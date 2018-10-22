import { Component, OnInit } from '@angular/core';
import {ArtworkStockService} from "../../../_services/artwork-stock.service";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-profile-gallery-artwork-stock',
  templateUrl: './artwork-stock.component.html',
  styleUrls: ['./artwork-stock.component.css']
})
export class ArtworkStockComponent implements OnInit {

  artworkStocks: ArtworkStockDto[];

  public url = environment.baseUrl;

  constructor(private artworkStockService: ArtworkStockService, public translate: TranslateService) { }

  ngOnInit() {
    this.getArtworkStock();
  }

  getArtworkStock(): void {
    this.artworkStockService
      .getArtworkStock()
      .subscribe(
        data => (this.artworkStocks = data)
      );
  }

}
