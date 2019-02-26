import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {ArtworkDto} from "../../../../_transfer";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkService} from "../../../../_services/artwork.service";

@Component({
  selector: 'app-artworks-list',
  templateUrl: './artwork-list.component.html',
  styleUrls: ['./artwork-list.component.css']
})
export class ArtworkListComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  artworks: ArtworkDto[];

  selectedRowIndex: number = -1;


  constructor(private artworkService: ArtworkService,
              private router: Router,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getArtworks();
  }

  getArtworks(): void {
    this.artworkService
      .getArtworks()
      .subscribe(
        data => (this.artworks = data)
      );
  }


  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  onAddButton() {
    this.router.navigate(['/admin/cms/artworks/edit'], {
      queryParams: {
        "new": true
      }
    });
  }

  onEditButton() {
    if (this.selectedRowIndex < 0)
      return;

    const artwork = this.artworks[this.selectedRowIndex];

    this.router.navigate(['/admin/cms/artworks/edit'], {
      queryParams: {
        "id": artwork.id
      }
    });
  }
}
