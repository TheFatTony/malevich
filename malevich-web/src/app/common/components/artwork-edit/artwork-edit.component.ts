import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ArtistDto, ArtworkDto, CategoryDto} from "../../../_transfer";
import {ArtworkService} from "../../../_services/artwork.service";
import {ArtistService} from "../../../_services/artist.service";
import {CategoryService} from "../../../_services/category.service";
import {TranslateService} from "@ngx-translate/core";
import {FileDto} from "yinyang-core/lib/_transfer/fileDto";
import {environment} from "../../../../environments/environment.dev";

@Component({
  selector: 'app-artwork-edit',
  templateUrl: './artwork-edit.component.html',
  styleUrls: ['./artwork-edit.component.css']
})
export class ArtworkEditComponent implements OnInit, AfterViewInit {

  @Output() onSubmit = new EventEmitter();
  @Output() onCancel = new EventEmitter();

  @Input('artwork')
  set artworkSetter(value: ArtworkDto) {
    this.artwork = value;
  }

  @ViewChild('artist') artistComboBox: any;
  @ViewChild('category') categoryComboBox: any;

  public url = environment.baseUrl;

  artwork: ArtworkDto;

  artists: ArtistDto[];
  categories: CategoryDto[];

  artistDisplayFunc = (artist: ArtistDto) => {
    return artist.fullNameMl[this.translate.currentLang];
  };

  categoryDisplayFunc = (category: CategoryDto) => {
    return category.categoryNameMl[this.translate.currentLang];
  };

  constructor(private artworkService: ArtworkService,
              private artistService: ArtistService,
              private categoryService: CategoryService,
              private translate: TranslateService) {
  }

  ngOnInit() {
    this.getArtists();
    this.getCategories();
  }

  ngAfterViewInit(): void {
    // set button types to 'button' to avoid unattended form submit
    $('jqxfileupload').find('button').attr('type', 'button');
  }

  getArtists() {
    this.artistService
      .getArtists()
      .subscribe(data => {
        this.artists = data;
      });
  }

  getCategories() {
    this.categoryService
      .getCategories()
      .subscribe(data => {
        this.categories = data;
      });
  }

  submit() {
    if (!this.artwork || !this.artwork.image || !this.artwork.thumbnail || !this.artwork.artist || !this.artwork.category)
      return;

    this.onSubmit.emit(this.artwork);
  }

  cancel() {
    this.onCancel.emit();
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
