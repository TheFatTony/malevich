import {Component, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {ArtworkStockDto} from "../../_transfer/artworkStockDto";
import {environment} from "../../../environments/environment.dev";
import {ArtworkDto} from "../../_transfer/index";
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {Router} from "@angular/router";
import {AccountStateService} from "../../_services/account-state.service";
import {OrderDto} from "../../_transfer/orderDto";
import {OrderWindowComponent} from "../../common/components/order-window/order-window.component";

@Component({
  selector: 'app-profile-artwork-stock',
  templateUrl: './artwork-stock.component.html',
  styleUrls: ['./artwork-stock.component.css']
})
export class ArtworkStockComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('addArtWorkComboBox') addArtWorkComboBox: jqxComboBoxComponent;
  @ViewChild('askWindow') askWindow: OrderWindowComponent

  artworks: ArtworkDto[];
  artworkStocks: ArtworkStockDto[];

  selectedRowIndex: number = -1;

  x: number;
  y: number;

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

  constructor(private router: Router,
              private accountStateService: AccountStateService,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getArtworkStock();
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

  getArtworkStock(): void {
    this.accountStateService
      .getOwnArtworks()
      .subscribe(
        data => {
          this.artworkStocks = data;
        }
      );
  }

  openAskWindow() {
    if (this.selectedRowIndex < 0)
      return;

    let artwork = this.artworkStocks[this.selectedRowIndex];

    this.askWindow.artworkStock(artwork);
    this.askWindow.open();
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  onAskPlaced(order: OrderDto) {
    this.getArtworkStock();
  }
}
