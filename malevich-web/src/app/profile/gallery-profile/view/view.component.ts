import {Component, OnInit} from '@angular/core';
import {GalleryService} from "../../../_services/gallery.service";
import {GalleryDto} from "../../../_transfer";
import {environment} from "../../../../environments/environment";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-profile-gallery-security-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  gallery: GalleryDto;

  public url = environment.baseUrl;

  constructor(public translate: TranslateService,
              private galleryService: GalleryService) {
  }

  ngOnInit() {
    this.getGallery();
  }

  getGallery(): void {
    this.galleryService
      .getGallery()
      .subscribe(
        data => (this.gallery = data)
      );
  }

}
