import { Component, OnInit } from '@angular/core';
import {ArtworkDto} from "../../_transfer";
import {environment} from "../../../environments/environment";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkService} from "../../_services/artwork.service";

@Component({
  selector: 'app-artworks-list',
  templateUrl: './artworks-list.component.html',
  styleUrls: ['./artworks-list.component.css']
})
export class ArtworksListComponent implements OnInit {

  showGrid: boolean = true;
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
