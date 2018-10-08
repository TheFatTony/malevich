import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ArtworkDto} from "../../_transfer";
import {environment} from "../../../environments/environment";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkService} from "../../_services/artwork.service";
import {ActivatedRoute, Params} from "@angular/router";

@Component({
  selector: 'app-artworks-detail',
  templateUrl: './artworks-detail.component.html',
  styleUrls: ['./artworks-detail.component.css']
})
export class ArtworksDetailComponent implements OnInit, AfterViewInit {

  artwork: ArtworkDto;
  id: number;

  public url = environment.baseUrl;

  constructor(private route: ActivatedRoute,
              public translate: TranslateService,
              private artworkService: ArtworkService) {
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getArtwork();
  }

  ngAfterViewInit(): void {
    $['HSCore'].helpers.HSRating.init();
  }

  getArtwork(): void {
    this.artworkService
      .getArtwork(this.id)
      .subscribe(
        data => (this.artwork = data)
      );
  }

}
