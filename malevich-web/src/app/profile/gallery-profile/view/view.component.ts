import {Component, OnInit} from '@angular/core';
import {GalleryService} from "../../../_services/gallery.service";
import {GalleryDto} from "../../../_transfer";
import {environment} from "../../../../environments/environment.dev";
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";
import {AuthService} from "../../../_services";

@Component({
  selector: 'app-profile-gallery-security-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  gallery: GalleryDto;

  changePassword = false;
  oldPassword: string;
  newPassword: string;

  public url = environment.baseUrl;

  constructor(private router: Router,
              public translate: TranslateService,
              private galleryService: GalleryService,
              private authService: AuthService) {
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

  edit() {
    this.router.navigate(['/profile/gallery/edit']).then();
  }

  switchChangePassword() {
    this.changePassword = !this.changePassword;
  }

  onChangePassword() {
    this.authService.changePassword(this.oldPassword, this.newPassword).subscribe();
    this.switchChangePassword();
  }
}
