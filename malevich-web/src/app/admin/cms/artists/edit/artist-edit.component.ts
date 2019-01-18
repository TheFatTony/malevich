import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ArtistService} from "../../../../_services/artist.service";
import {ArtistDto} from "../../../../_transfer";
import {environment} from "../../../../../environments/environment.dev";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-artists-edit',
  templateUrl: './artist-edit.component.html',
  styleUrls: ['./artist-edit.component.css']
})
export class ArtistEditComponent implements OnInit {

  editArtist: ArtistDto;

  isNew: boolean = false;

  public url = environment.baseUrl;

  constructor(private artistService: ArtistService,
              private route: ActivatedRoute,
              private router: Router,
              public translate: TranslateService) {
  }

  ngOnInit() {
    let params = this.route.snapshot.queryParams;
    this.isNew = ("true" == params['new']);

    if (this.isNew) {
      this.editArtist = new ArtistDto();
      this.editArtist.fullNameMl = new Map<string, string>();
      this.editArtist.descriptionMl = new Map<string, string>();
    } else {
      let id = params['id'];
      if (id != null)
        this.artistService.getArtist(id).subscribe(data => {
          this.editArtist = data;
        })
    }

  }


  sendButton() {
    this.artistService.saveArtist(this.editArtist).subscribe(
      () => {
        this.router.navigate(['/admin/cms/artists']);
      }
    );
  }

  onNameChange($event, lang: string) {
    if (!$event)
      return;

    this.editArtist.fullNameMl[lang] = $event;
  }

  onDescriptionChange($event, lang: string) {
    if (!$event || !this.editArtist)
      return;

    this.editArtist.descriptionMl[lang] = $event;
  }

  private parseFileResponse(event: any) {
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

  onThumbnailUploadEnd(event: any): void {
    this.editArtist.thumbnail = this.parseFileResponse(event);
  }
}
