import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {ArtworkDto} from "../../../../_transfer";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkService} from "../../../../_services/artwork.service";
import {environment} from "../../../../../environments/environment.dev";

@Component({
  selector: 'app-artworks-list',
  templateUrl: './artwork-list.component.html',
  styleUrls: ['./artwork-list.component.css']
})
export class ArtworkListComponent implements OnInit, AfterViewInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  artworks: ArtworkDto[];

  selectedRowIndex: number = -1;

  public url = environment.baseUrl;

  photoRenderer = (row: number, column: any, value: string): string => {
    let data = this.artworks[row];
    let imgurl = this.url + data.thumbnail.url;
    let img = '<div style="background: white;"><img style="margin: 2px; margin-left: 10px;" width="48" height="48" src="' + imgurl + '"></div>';
    return img;
  };

  renderer = (row: number, column: any, value: string): string => {
    return '<span style="margin-left: 4px; margin-top: 15px; float: left;">' + value + '</span>';
  };

  columns(names: any): any[] {
    return [
      {
        dataField: 'Image',
        text: names['PROFILE.GRID.IMAGE'],
        width: '10%',
        cellsrenderer: this.photoRenderer
      },
      {
        dataField: 'Title',
        text: names['PROFILE.GRID.TITLE'],
        width: '40%',
        cellsrenderer: this.renderer
      },
      {
        dataField: 'Artist',
        text: names['PROFILE.GRID.ARTIST'],
        width: '25%',
        cellsrenderer: this.renderer
      },
      {
        dataField: 'Category',
        text: names['PROFILE.GRID.CATEGORY'],
        width: '25%',
        cellsrenderer: this.renderer
      }
    ];
  }

  constructor(private artworkService: ArtworkService,
              private router: Router,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getArtworks();
  }

  ngAfterViewInit(): void {
    this.updateGrid();
  }

  updateGrid() {
    this.translate
      .get([
        'PROFILE.GRID.IMAGE',
        'PROFILE.GRID.TITLE',
        'PROFILE.GRID.ARTIST',
        'PROFILE.GRID.CATEGORY'
      ])
      .subscribe(data => {
        this.myGrid.hideloadelement();
        this.myGrid.beginupdate();
        this.myGrid.setOptions
        ({
          columns: this.columns(data)
        });
        this.myGrid.endupdate();
      });
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
