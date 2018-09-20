import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ArtworkDto} from "../../../_transfer";
import {environment} from "../../../../environments/environment";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkService} from "../../../_services/artwork.service";

@Component({
  selector: 'app-artworks-artworks-list-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit, AfterViewInit {

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
