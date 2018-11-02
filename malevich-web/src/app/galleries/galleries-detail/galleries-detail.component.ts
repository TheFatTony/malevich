import {Component, OnInit} from '@angular/core';
import {GalleryDto} from "../../_transfer";
import {environment} from "../../../environments/environment";
import {ActivatedRoute, Params} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {GalleryService} from "../../_services/gallery.service";

@Component({
  selector: 'app-galleries-detail',
  templateUrl: './galleries-detail.component.html',
  styleUrls: ['./galleries-detail.component.css']
})
export class GalleriesDetailComponent implements OnInit {

  gallery: GalleryDto;
  id: number;

  public url = environment.baseUrl;

  constructor(private route: ActivatedRoute,
              public translate: TranslateService,
              private galleryService: GalleryService) {
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getGallery();
  }

  getGallery(): void {
    this.galleryService
      .getGalleryById(this.id)
      .subscribe(
        data => (this.gallery = data)
      );
  }

}
