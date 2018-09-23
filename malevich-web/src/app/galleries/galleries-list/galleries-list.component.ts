import {AfterViewInit, Component, OnInit} from '@angular/core';
import {GalleryDto} from "../../_transfer";
import {environment} from "../../../environments/environment";
import {TranslateService} from "@ngx-translate/core";
import {GalleryService} from "../../_services/gallery.service";

@Component({
  selector: 'app-galleries-galleries-list',
  templateUrl: './galleries-list.component.html',
  styleUrls: ['./galleries-list.component.css']
})
export class GalleriesListComponent implements OnInit, AfterViewInit {

  showGrid: boolean = true;
  galleries: GalleryDto[];

  private url = environment.baseUrl;

  constructor(public translate: TranslateService, private galleryService: GalleryService) {
  }

  ngOnInit() {
    this.getGalleries();
  }

  ngAfterViewInit(): void {
  }

  getGalleries(): void {
    this.galleryService
      .getGalleries()
      .subscribe(
        data => (this.galleries = data)
      );
  }

}
