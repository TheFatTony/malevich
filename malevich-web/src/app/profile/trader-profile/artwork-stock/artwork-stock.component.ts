import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {environment} from "../../../../environments/environment.dev";
import {ArtworkDto, GalleryDto} from "../../../_transfer";
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {Router} from "@angular/router";
import {AccountStateService} from "../../../_services/account-state.service";

@Component({
  selector: 'app-profile-trader-artwork-stock',
  templateUrl: './artwork-stock.component.html',
  styleUrls: ['./artwork-stock.component.css']
})
export class ArtworkStockComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('addArtWorkComboBox') addArtWorkComboBox: jqxComboBoxComponent;

  gallery: GalleryDto;
  artworks: ArtworkDto[];
  artworkStocks: ArtworkStockDto[];

  public addArtWorkComboBoxSource: any[];

  selectedRowIndex: number = -1;

  x: number;
  y: number;

  public addArtworkStock: ArtworkStockDto;

  public url = environment.baseUrl;

  photoRenderer = (row: number, column: any, value: string): string => {
    let data = this.artworkStocks[row];
    let imgurl = this.url + data.artwork.thumbnail.url;
    let img = '<div style="background: white;"><img style="margin: 2px; margin-left: 10px;" width="48" height="48" src="' + imgurl + '"></div>';
    return img;
  };

  renderer = (row: number, column: any, value: string): string => {
    return '<span style="margin-left: 4px; margin-top: 15px; float: left;">' + value + '</span>';
  };

  rowdetailstemplate: any = {
    rowdetails: "<div>{{a.artwork.descriptionMl[translate.currentLang]}}</div>",
    rowdetailsheight: 50,
    rowdetailshidden: true
  };

  columns: any[] =
    [
      {datafield: 'Image', width: '10%', cellsrenderer: this.photoRenderer},
      {datafield: 'Title', width: '40%', cellsrenderer: this.renderer},
      {datafield: 'Artist', width: '25%', cellsrenderer: this.renderer},
      {datafield: 'Category', width: '25%', cellsrenderer: this.renderer}
    ];

  constructor(private router: Router,
              private accountStateService: AccountStateService,
              public translate: TranslateService) {
    }

  ngOnInit() {
    this.getArtworkStock();
  }

  getArtworkStock(): void {
    this.accountStateService
      .getTraderArtworks()
      .subscribe(
        data => {
          this.artworkStocks = data;
        }
      );
  }

  onAddArtworkSelect(event: any) {
    let selectedIndex = this.addArtWorkComboBox.selectedIndex();
    if (selectedIndex == -1)
      return;
    this.addArtworkStock.artwork = this.artworks[selectedIndex];
  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

}
