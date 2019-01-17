import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {TranslateService} from '@ngx-translate/core';
import {jqxWindowComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow';
import {ArtistService} from "../../../../_services/artist.service";
import {ArtistDto} from "../../../../_transfer";
import {environment} from "../../../../../environments/environment.dev";

@Component({
  selector: 'app-help-category-list',
  templateUrl: './artist-list.component.html',
  styleUrls: ['./artist-list.component.css']
})
export class ArtistListComponent implements OnInit {
  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  artists: ArtistDto[];
  editArtist: ArtistDto;

  selectedRowIndex: number;

  public url = environment.baseUrl;

  x: number;
  y: number;

  constructor(private artistService: ArtistService,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getArtists();
  }

  getArtists(): void {
    this.artistService
      .getArtists()
      .subscribe(
        data => (this.artists = data)
      );
  }

  openWindow() {
    this.myWindow.width(500);
    this.myWindow.height(800);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  sendButton() {
    this.artistService.saveArtist(this.editArtist).subscribe(
      () => {
        this.getArtists();
        this.myWindow.close();
        this.myGrid.refresh();
        this.editArtist = null;
      }
    );
  }

  onNameChange($event, lang: string) {
    if (!$event)
      return;

    this.editArtist.fullNameMl[lang] = $event;
  }

  onDescriptionChange($event, lang: string) {
    if (!$event)
      return;

    this.editArtist.descriptionMl[lang] = $event;
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  onMyWindowOpen() {

  }

  onAddButton() {
    this.editArtist = new ArtistDto();
    this.editArtist.fullNameMl = new Map<string, string>();
    this.editArtist.descriptionMl = new Map<string, string>();

    this.openWindow();
  }

  onEditButton() {
    if (this.selectedRowIndex < 0)
      return;

    this.editArtist = this.artists[this.selectedRowIndex];

    this.openWindow();
  }

  private parseFileResponse(event:any){
    let args = event.args;
    let body = args.response.toString()
      .replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">', '')
      .replace('<pre>', '')
      .replace('</pre>', '');

    let serverResponse = JSON.parse(body);
    return serverResponse;
  }

  onImageUploadEnd(event: any): void {
    this.editArtist.image = this.parseFileResponse(event);
  }

  onThmbnailUploadEnd(event: any): void {
    this.editArtist.thumbnail = this.parseFileResponse(event);
  }
}
