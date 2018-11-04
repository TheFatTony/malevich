import {Component, OnInit, ViewChild} from '@angular/core';
import {ArtworkDto, GalleryDto} from "../../../../_transfer";
import {ArtworkStockDto} from "../../../../_transfer/artworkStockDto";
import {GalleryService} from "../../../../_services/gallery.service";
import {ArtworkStockService} from "../../../../_services/artwork-stock.service";
import {ArtworkService} from "../../../../_services/artwork.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {ArtistService} from "../../../../_services/artist.service";
import {CategoryService} from "../../../../_services/category.service";

@Component({
  selector: 'app-profile-gallery-artwork-stock-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  @ViewChild('addArtWorkComboBox') addArtWorkComboBox: jqxComboBoxComponent;
  @ViewChild('artistComboBox') artistComboBox: jqxComboBoxComponent;
  @ViewChild('categoryComboBox') categoryComboBox: jqxComboBoxComponent;

  gallery: GalleryDto;

  artworkStock: ArtworkStockDto;
  public artwork: ArtworkDto;

  artists: any[];
  categories: any[];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private galleryService: GalleryService,
              private artworkStockService: ArtworkStockService,
              private artworkService: ArtworkService,
              private artistService: ArtistService,
              private categoryService: CategoryService,
              public translate: TranslateService) {
    $.jqx.theme = 'malevich';
  }

  ngOnInit() {
    this.getArtists();
    this.getCategories();

    this.route.params.subscribe((params: Params) => {
      let id = params['id'];
      this.artworkStockService.getArtworkStock(id)
        .subscribe(a => {
          this.artworkStock = a;
          this.artwork = a.artwork;

          let artistIndex = this.artists.findIndex(value => value.value.id == this.artwork.artist.id);
          this.artistComboBox.selectIndex(artistIndex);

          let categoryIndex = this.categories.findIndex(value => value.value.id == this.artwork.category.id);
          this.categoryComboBox.selectIndex(categoryIndex);
        })
    });
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
    this.artworkStockService.addArtworkStock(this.artworkStock).subscribe();
    this.router.navigate(['/profile/gallery/artworkstok']);
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
