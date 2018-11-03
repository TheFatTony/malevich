import {Component, OnInit} from '@angular/core';
import {ArtistDto} from "../../_transfer";
import {environment} from "../../../environments/environment.dev";
import {ActivatedRoute, Params} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {ArtistService} from "../../_services/artist.service";

@Component({
  selector: 'app-artists-detail',
  templateUrl: './artists-detail.component.html',
  styleUrls: ['./artists-detail.component.css']
})
export class ArtistsDetailComponent implements OnInit {

  artist: ArtistDto;
  id: number;

  public url = environment.baseUrl;

  constructor(private route: ActivatedRoute,
              public translate: TranslateService,
              private artistService: ArtistService) {
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getArtist();
  }

  ngAfterViewInit(): void {
  }

  getArtist(): void {
    this.artistService
      .getArtist(this.id)
      .subscribe(
        data => (this.artist = data)
      );
  }

}
