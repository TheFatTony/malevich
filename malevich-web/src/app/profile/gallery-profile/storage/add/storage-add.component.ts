import {Component, OnInit, ViewChild} from '@angular/core';
import {ArtistDto, ArtworkDto, CategoryDto, GalleryDto} from "../../../../_transfer";
import {ArtworkStockDto} from "../../../../_transfer/artworkStockDto";
import {GalleryService} from "../../../../_services/gallery.service";
import {ArtworkStockService} from "../../../../_services/artwork-stock.service";
import {ArtworkService} from "../../../../_services/artwork.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {Router} from "@angular/router";
import {ArtistService} from "../../../../_services/artist.service";
import {CategoryService} from "../../../../_services/category.service";

@Component({
  selector: 'app-profile-gallery-storage-add',
  templateUrl: './storage-add.component.html',
  styleUrls: ['./storage-add.component.css']
})
export class StorageAddComponent implements OnInit {

  @ViewChild('addArtWorkComboBox') addArtWorkComboBox: jqxComboBoxComponent;
  @ViewChild('artistComboBox') artistComboBox: jqxComboBoxComponent;
  @ViewChild('categoryComboBox') categoryComboBox: jqxComboBoxComponent;

  gallery: GalleryDto;

  public artwork: ArtworkDto;

  artworks: ArtworkDto[];
  artists: ArtistDto[];
  categories: CategoryDto[];

  artistDisplayFunc = (artist: ArtistDto) => {
    return artist.fullNameMl[this.translate.currentLang];
  };

  categoryDisplayFunc = (category: CategoryDto) =>{
    return category.categoryNameMl[this.translate.currentLang];
  };

  artworkDisplayFunc = (artwork:ArtworkDto) =>{
    return artwork.titleMl[this.translate.currentLang];
  };

  constructor(private router: Router,
              private galleryService: GalleryService,
              private artworkStockService: ArtworkStockService,
              public artworkService: ArtworkService,
              private artistService: ArtistService,
              private categoryService: CategoryService,
              public translate: TranslateService) {
    }

  ngOnInit() {
    this.getArtworks();
    this.getArtists();
    this.getCategories();
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

  getArtists() {
    this.artistService
      .getArtists()
      .subscribe(data => (
        this.artists = data
      ));
  }

  getCategories() {
    this.categoryService
      .getCategories()
      .subscribe(data => (
        this.categories = data
      ));
  }

  submit() {
    let addArtworkStock = new ArtworkStockDto();
    addArtworkStock.artwork = this.artwork;
    addArtworkStock.gallery = this.gallery;
    this.artworkStockService.addArtworkStock(addArtworkStock).subscribe(() => {
      this.router.navigate(['/profile/gallery/artworkstok']);
    });
  }

  onArtworkComboBoxChange($event) {
    if (!$event)
      return;

    this.artwork = $event;
    this.addArtWorkComboBox.disabled(true);
  }

  cancel() {
    this.router.navigate(['/profile/gallery/artworkstok']);
  }

  onArtistComboBoxChange($event) {
    if (!$event)
      return;

    if (!this.artwork)
      this.artwork = new ArtworkDto();

    this.artwork.artist = $event;
  }

  onCategoryComboBoxChange($event) {
    if (!$event)
      return;

    if (!this.artwork)
      this.artwork = new ArtworkDto();

    this.artwork.category = $event;
  }

  onDescriptionEditorChange($event, lang: string) {
    if (!$event)
      return;

    if (!this.artwork)
      this.artwork = new ArtworkDto();

    if (!this.artwork.descriptionMl)
      this.artwork.descriptionMl = new Map<string, string>();

    this.artwork.descriptionMl[lang] = $event
  }

  onTitleEditorChange($event, lang: string) {
    if (!$event)
      return;

    if (!this.artwork)
      this.artwork = new ArtworkDto();

    if (!this.artwork.titleMl)
      this.artwork.titleMl = new Map<string, string>();

    this.artwork.titleMl[lang] = $event
  }
}
