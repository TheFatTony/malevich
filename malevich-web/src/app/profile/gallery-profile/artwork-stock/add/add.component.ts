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
  selector: 'app-profile-gallery-artwork-stock-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  @ViewChild('addArtWorkComboBox') addArtWorkComboBox: jqxComboBoxComponent;
  @ViewChild('artistComboBox') artistComboBox: jqxComboBoxComponent;
  @ViewChild('categoryComboBox') categoryComboBox: jqxComboBoxComponent;

  gallery: GalleryDto;

  public artwork: ArtworkDto;

  public addArtWorkComboBoxSource: any[];
  artists: any[];
  categories: any[];

  constructor(private router: Router,
              private galleryService: GalleryService,
              private artworkStockService: ArtworkStockService,
              private artworkService: ArtworkService,
              private artistService: ArtistService,
              private categoryService: CategoryService,
              public translate: TranslateService) {
    $.jqx.theme = 'malevich';
  }

  ngOnInit() {
    this.getArtworks();
    //this.artwork = this.getDefaultArtWork();
    // this.artwork.titleMl = new Map<string, string>();
    this.getArtists();
    this.getCategories();
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
          this.addArtWorkComboBoxSource = data.map(artwork => ({
              id: artwork,
              title: artwork.titleMl[this.translate.currentLang],
              html: '<table style="min-width: 50px; width: 100%"><tr><td style="width: 100px;" rowspan="2">' +
                '<img class="img-fluid" src="https://via.placeholder.com/50x50/img8.jpg" alt="Image Description">' +
                '</td><td>' + '<span class="d-block g-color-gray-dark-v4">' + artwork.titleMl[this.translate.currentLang] + '</span>' + '</td></tr><tr><td>' +
                '<span class="d-block g-color-lightred">' + artwork.category.categoryNameMl[this.translate.currentLang] + '</span>' + '</td></tr></table>'
            })
          );

          // console.info(this.addArtWorkComboBoxSource);
        }
      );
  }

  getArtists() {
    this.artistService
      .getArtists()
      .subscribe(data => (
        this.artists = data.map(i => ({
          title: i.fullNameMl[this.translate.currentLang],
          value: i
        }))
      ));
  }

  getCategories() {
    this.categoryService
      .getCategories()
      .subscribe(data => (
        this.categories = data.map(i => ({
          title: i.categoryNameMl[this.translate.currentLang],
          value: i
        }))
      ));
  }

  submit() {
    let addArtworkStock = new ArtworkStockDto();
    addArtworkStock.artwork = this.artwork;
    addArtworkStock.gallery = this.gallery;
    this.artworkStockService.addArtworkStock(addArtworkStock).subscribe();
    this.router.navigate(['/profile/gallery/artworkstok']);
  }

  onArtworkComboBoxChange($event) {
    // console.info($event);

    if (!$event)
      return;

    this.artwork = $event;

    let artistIndex = this.artists.findIndex(value => value.value.id == this.artwork.artist.id);
    this.artistComboBox.selectIndex(artistIndex);

    let categoryIndex = this.categories.findIndex(value => value.value.id == this.artwork.category.id);
    this.categoryComboBox.selectIndex(categoryIndex);

    // console.info(this.artwork);
    this.addArtWorkComboBox.disabled(true);
  }

  cancel() {
    this.router.navigate(['/profile/gallery/artworkstok']);
  }

  onArtistComboBoxChange($event) {
    // console.info($event);

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
  
  onDescriptionEditorChange($event) {
    if (!$event)
      return;

    if (!this.artwork)
      this.artwork = new ArtworkDto();

    if (!this.artwork.descriptionMl)
      this.artwork.descriptionMl = new Map<string, string>();

    this.artwork.descriptionMl['en'] = $event
  }

  onTitleEditorChange($event) {
    if (!$event)
      return;

    if (!this.artwork)
      this.artwork = new ArtworkDto();

    if (!this.artwork.titleMl)
      this.artwork.titleMl = new Map<string, string>();

    this.artwork.titleMl['en'] = $event
  }
}
