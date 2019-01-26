import {Component, OnInit, ViewChild} from '@angular/core';
import {ArtistDto, ArtworkDto, CategoryDto, GalleryDto} from "../../../_transfer";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {ArtworkStockService} from "../../../_services/artwork-stock.service";
import {ArtworkService} from "../../../_services/artwork.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {ArtistService} from "../../../_services/artist.service";
import {CategoryService} from "../../../_services/category.service";
import {FileDto} from "yinyang-core";
import {environment} from "../../../../environments/environment.dev";

@Component({
  selector: 'app-profile-storage-edit',
  templateUrl: './storage-edit.component.html',
  styleUrls: ['./storage-edit.component.css']
})
export class StorageEditComponent implements OnInit {

  @ViewChild('addArtWorkComboBox') addArtWorkComboBox: jqxComboBoxComponent;

  public url = environment.baseUrl;

  gallery: GalleryDto;

  artworkStock: ArtworkStockDto;
  public artwork: ArtworkDto;

  artists: ArtistDto[];
  categories: CategoryDto[];

  artistDisplayFunc = (artist: ArtistDto) => {
    return artist.fullNameMl[this.translate.currentLang];
  };

  categoryDisplayFunc = (category: CategoryDto) =>{
    return category.categoryNameMl[this.translate.currentLang];
  };

  constructor(private router: Router,
              private route: ActivatedRoute,
              private artworkStockService: ArtworkStockService,
              private artworkService: ArtworkService,
              private artistService: ArtistService,
              private categoryService: CategoryService,
              public translate: TranslateService) {
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
        })
    });
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
    if (!this.artwork || !this.artwork.image || !this.artwork.thumbnail || !this.artwork.artist || !this.artwork.category)
      return;

    this.artworkStockService.updateArtworkStock(this.artworkStock).subscribe();
    this.router.navigate(['/profile/storage']);
  }

  cancel() {
    this.router.navigate(['/profile/storage']);
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

  private parseUploadEvent(event: any): FileDto {
    let args = event.args;
    return JSON.parse(args.response.toString()
      .replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">', '')
      .replace('<pre>', '')
      .replace('</pre>', ''));
  }

  onUploadImage($event: any) {
    this.artwork.image = this.parseUploadEvent($event);
  }

  onUploadThumbnail($event: any) {
    this.artwork.thumbnail = this.parseUploadEvent($event);
  }
}
