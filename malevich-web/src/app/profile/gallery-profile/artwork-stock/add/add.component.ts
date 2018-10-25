import {Component, OnInit, ViewChild} from '@angular/core';
import {ArtworkDto, GalleryDto} from "../../../../_transfer";
import {ArtworkStockDto} from "../../../../_transfer/artworkStockDto";
import {GalleryService} from "../../../../_services/gallery.service";
import {ArtworkStockService} from "../../../../_services/artwork-stock.service";
import {ArtworkService} from "../../../../_services/artwork.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {Router} from "@angular/router";

@Component({
  selector: 'app-profile-gallery-artwork-stock-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  @ViewChild('addArtWorkComboBox') addArtWorkComboBox: jqxComboBoxComponent;

  gallery: GalleryDto;
  artworks: ArtworkDto[];

  public addArtwork: ArtworkDto;
  public selectedArtwork: ArtworkDto;
  public artworkStock: ArtworkStockDto;
  public addArtWorkComboBoxSource: any[];

  constructor(private router: Router,
              private galleryService: GalleryService,
              private artworkStockService: ArtworkStockService,
              private artworkService: ArtworkService,
              public translate: TranslateService) {
    $.jqx.theme = 'malevich';
  }

  ngOnInit() {
    this.getArtworks();
    //this.addArtwork = this.getDefaultArtWork();
    // this.addArtwork.titleMl = new Map<string, string>();
  }

  getDefaultArtWork() {
    let artwork = new ArtworkDto();
    // artwork.titleMl = new Map<string, string>();
    // artwork.descriptionMl = new Map<string, string>();
    return artwork;
  }

  getArtworks(): void {
    this.artworkService
      .getArtworks()
      .subscribe(
        data => {
          this.artworks = data;
          this.addArtWorkComboBoxSource = data.map(artwork => ({
              id: artwork,
              title: artwork.titleMl[this.translate.currentLang],
              html: '<table style="min-width: 50px; width: 100%"><tr><td style="width: 100px;" rowspan="2">' +
                '<img class="img-fluid" src="https://via.placeholder.com/50x50/img8.jpg" alt="Image Description">' +
                '</td><td>' + '<span class="d-block g-color-gray-dark-v4">' + artwork.titleMl[this.translate.currentLang] + '</span>' + '</td></tr><tr><td>' +
                '<span class="d-block g-color-lightred">' + artwork.category.categoryNameMl[this.translate.currentLang] + '</span>' + '</td></tr></table>'
            })
          );
          // this.addArtWorkComboBoxSource.unshift(this.getDefaultArtWork());
        }
      );
  }

  submit() {
    let addArtworkStock = new ArtworkStockDto();
    addArtworkStock.artwork = this.artworkStock.artwork;
    addArtworkStock.gallery = this.gallery;
    this.artworkStockService.addArtworkStock(addArtworkStock).subscribe();
    this.router.navigate(['/profile/gallery/artworkstok']);
  }

  onArtworkComboBoxChange($event) {
    this.selectedArtwork = $event;
    if (this.selectedArtwork) {
      this.artworkStock = new ArtworkStockDto();
      this.artworkStock.artwork = this.selectedArtwork;
      this.addArtWorkComboBox.disabled(true);
    }
  }

  cancel() {
    this.router.navigate(['/profile/gallery/artworkstok']);
  }
}
