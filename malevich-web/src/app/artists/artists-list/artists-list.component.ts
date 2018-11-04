import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ArtistDto} from "../../_transfer";
import {environment} from "../../../environments/environment.dev";
import {TranslateService} from "@ngx-translate/core";
import {ArtistService} from "../../_services/artist.service";

@Component({
  selector: 'app-artists-list',
  templateUrl: './artists-list.component.html',
  styleUrls: ['./artists-list.component.css']
})
export class ArtistsListComponent implements OnInit, AfterViewInit {

  showGrid: boolean = true;
  artists: ArtistDto[];

  private url = environment.baseUrl;

  constructor(public translate: TranslateService, private artistService: ArtistService) {
  }

  ngOnInit() {
    this.getArtists();
  }

  ngAfterViewInit(): void {
  }

  getArtists(): void {
    this.artistService
      .getArtists()
      .subscribe(
        data => (this.artists = data)
      );
  }

}
