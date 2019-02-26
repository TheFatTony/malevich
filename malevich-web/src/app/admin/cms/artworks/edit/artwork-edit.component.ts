import {Component, OnInit} from '@angular/core';
import {ArtworkDto} from "../../../../_transfer";
import {environment} from "../../../../../environments/environment.dev";
import {ActivatedRoute, Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {ArtworkService} from "../../../../_services/artwork.service";

@Component({
  selector: 'app-edit',
  templateUrl: './artwork-edit.component.html',
  styleUrls: ['./artwork-edit.component.css']
})
export class ArtworkEditComponent implements OnInit {


  editArtwork: ArtworkDto;

  isNew: boolean = false;

  public url = environment.baseUrl;

  constructor(private artworkService: ArtworkService,
              private route: ActivatedRoute,
              private router: Router,
              public translate: TranslateService) {
  }

  ngOnInit() {
    let params = this.route.snapshot.queryParams;
    this.isNew = ("true" == params['new']);

    if (this.isNew) {
      this.editArtwork = new ArtworkDto();
      this.editArtwork.titleMl = new Map<string, string>();
      this.editArtwork.descriptionMl = new Map<string, string>();
    } else {
      let id = params['id'];
      if (id != null)
        this.artworkService.getArtwork(id).subscribe(data => {
          this.editArtwork = data;
        })
    }

  }


  submit(obj:ArtworkDto) {
    this.artworkService.saveArtwork(obj).subscribe(
      () => {
        this.router.navigate(['/admin/cms/artworks']);
      }
    );
  }

  cancel() {
    this.router.navigate(['/admin/cms/artworks']);
  }

}
