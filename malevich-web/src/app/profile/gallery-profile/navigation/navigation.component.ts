import { Component, OnInit } from '@angular/core';
import {GalleryDto} from "../../../_transfer";
import {GalleryService} from "../../../_services/gallery.service";
import {TranslateService} from "@ngx-translate/core";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-profile-gallery-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  gallery: GalleryDto;

  public url = environment.baseUrl;

  constructor(public translate: TranslateService,
              private galleryService: GalleryService) { }

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
