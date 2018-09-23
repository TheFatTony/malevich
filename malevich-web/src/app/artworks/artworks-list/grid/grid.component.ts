import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ArtworkDto} from "../../../_transfer";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkService} from "../../../_services/artwork.service";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-artworks-artworks-list-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit, AfterViewInit {

  artworks: ArtworkDto[];
  private url = environment.baseUrl;

  constructor(public translate: TranslateService, private artworkService: ArtworkService) {
  }

  ngOnInit() {
    this.getArtworks();
  }

  ngAfterViewInit(): void {
  }

  getArtworks(): void {
    this.artworkService
      .getArtworks()
      .subscribe(
        data => (this.artworks = data)
      );
  }

}
