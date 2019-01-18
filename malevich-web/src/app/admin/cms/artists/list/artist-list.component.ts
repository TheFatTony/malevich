import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {TranslateService} from '@ngx-translate/core';
import {ArtistService} from "../../../../_services/artist.service";
import {ArtistDto} from "../../../../_transfer";
import {Router} from "@angular/router";

@Component({
  selector: 'app-artists-list',
  templateUrl: './artist-list.component.html',
  styleUrls: ['./artist-list.component.css']
})
export class ArtistListComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  artists: ArtistDto[];

  selectedRowIndex: number = -1;


  constructor(private artistService: ArtistService,
              private router: Router,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getArtists();
  }

  getArtists(): void {
    this.artistService
      .getArtists()
      .subscribe(
        data => (this.artists = data)
      );
  }


  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  onAddButton() {
    this.router.navigate(['/admin/cms/artists/edit'], {
      queryParams: {
        "new": true
      }
    });
  }

  onEditButton() {
    if (this.selectedRowIndex < 0)
      return;

    let artist = this.artists[this.selectedRowIndex];

    this.router.navigate(['/admin/cms/artists/edit'], {
      queryParams: {
        "id": artist.id
      }
    });
  }
}
