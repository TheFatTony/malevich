import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {ArtworkStockService} from "../../../_services/artwork-stock.service";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {environment} from "../../../../environments/environment";
import {ArtworkService} from "../../../_services/artwork.service";
import {ArtworkDto, GalleryDto} from "../../../_transfer";
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {GalleryService} from "../../../_services/gallery.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-profile-gallery-artwork-stock',
  templateUrl: './artwork-stock.component.html',
  styleUrls: ['./artwork-stock.component.css']
})
export class ArtworkStockComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('addWindow') myWindow: jqxWindowComponent;
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

  constructor(private router: Router,
              private galleryService: GalleryService,
              private artworkStockService: ArtworkStockService,
              private artworkService: ArtworkService,
              public translate: TranslateService) {
    $.jqx.theme = 'malevich';
  }

  getArtworkComboBoxSource(array: ArtworkDto[]) {
    return array
      .map(artwork => ({
          title: artwork.titleMl[this.translate.currentLang],
          html: '<table style="min-width: 50px;"><tr><td style="width: 100px;" rowspan="2">' +
            '<img class="img-fluid" src="https://via.placeholder.com/50x50/img8.jpg" alt="Image Description">' +
            '</td><td>' + '<span class="d-block g-color-gray-dark-v4">' + artwork.titleMl[this.translate.currentLang] + '</span>' + '</td></tr><tr><td>' +
            '<span class="d-block g-color-lightred">' + artwork.category.categoryNameMl[this.translate.currentLang] + '</span>' + '</td></tr></table>'
        })
      );
  }

  ngOnInit() {
    this.getGallery();
    this.getArtworks();
    this.getArtworkStock();
  }

  getGallery(): void {
    this.galleryService
      .getGallery()
      .subscribe(
        data => (this.gallery = data)
      );
  }

  getArtworks(): void {
    this.artworkService
      .getArtworks()
      .subscribe(
        data => {
          this.artworks = data;
          this.addArtWorkComboBoxSource = this.getArtworkComboBoxSource(data);
        }
      );
  }

  getArtworkStock(): void {
    this.artworkStockService
      .getArtworkStock()
      .subscribe(
        data => {
          this.artworkStocks = data;
        }
      );
  }

  openAddWindow() {
    this.addArtworkStock = new ArtworkStockDto();
    this.addArtworkStock.gallery = this.gallery;
    this.addArtworkStock.artwork = new ArtworkDto();

    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  addButton() {
    console.info(this.addArtworkStock);
    this.artworkStockService.addArtworkStock(this.addArtworkStock)
      .subscribe(data => (this.getArtworkStock()));
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

  onAddButtonClick() {
    this.router.navigate(['/profile/gallery/artworkstok/add'])
  }

  onUpdateButtonClick() {

  }

  onDeleteButtonClick() {
    let deleted = this.artworkStocks.splice(this.selectedRowIndex, 1)[0];
    this.artworkStockService.deleteArtworkStock(deleted.id).subscribe();
    this.myGrid.refresh();
  }
}
