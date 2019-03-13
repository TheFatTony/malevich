import {Component, OnInit, ViewChild} from '@angular/core';
import {ArtistDto, ArtworkDto, CategoryDto, GalleryDto} from "../../../_transfer";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {GalleryService} from "../../../_services/gallery.service";
import {ArtworkStockService} from "../../../_services/artwork-stock.service";
import {ArtworkService} from "../../../_services/artwork.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment.dev";

@Component({
  selector: 'app-profile-storage-add',
  templateUrl: './storage-add.component.html',
  styleUrls: ['./storage-add.component.css']
})
export class StorageAddComponent implements OnInit {

  @ViewChild('addArtWorkComboBox') addArtWorkComboBox: jqxComboBoxComponent;

  public url = environment.baseUrl;

  gallery: GalleryDto;

  public artwork: ArtworkDto = new ArtworkDto();

  artworks: ArtworkDto[];

  artworkDisplayFunc = (artwork: ArtworkDto) => {
    return artwork.titleMl[this.translate.currentLang];
  };

  constructor(private router: Router,
              private galleryService: GalleryService,
              private artworkStockService: ArtworkStockService,
              public artworkService: ArtworkService,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getArtworks();
  }

  getArtworks(): void {
    this.artworkService
      .getArtworks()
      .subscribe(
        data => {
          this.artworks = data;
        }
      );
  }

  submit(obj: ArtworkDto) {
    let addArtworkStock = new ArtworkStockDto();
    addArtworkStock.artwork = obj;
    addArtworkStock.gallery = this.gallery;
    this.artworkStockService.addArtworkStock(addArtworkStock).subscribe(() => {
      this.router.navigate(['/profile/storage']);
    });
  }

  onArtworkComboBoxChange($event) {
    if (!$event)
      return;

    this.artwork = $event;
    this.addArtWorkComboBox.disabled(true);
  }

  cancel() {
    this.router.navigate(['/profile/storage']);
  }
}
