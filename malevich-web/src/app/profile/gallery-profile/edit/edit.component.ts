import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {CountryDto} from "../../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {CountryService} from "../../../_services/country.service";
import {GalleryDto} from "../../../_transfer";
import {GalleryService} from "../../../_services/gallery.service";
import {Router} from "@angular/router";
import {AlertService} from "../../../_services";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-profile-gallery-security-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {


  countries: CountryDto[];
  gallery: GalleryDto;

  @ViewChild ('mobileInput') mobileInput: ElementRef;

  constructor(public translate: TranslateService,
              private galleryService: GalleryService,
              private countryService: CountryService,
              private router: Router,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.getGallery();
    this.getCountries();
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSSelect.init('.js-custom-select');
    $['HSCore'].components.HSMaskedInput.init('[data-mask]');

    $(this.mobileInput.nativeElement).on('change', (e) => {
      this.gallery.organization.phoneNumber = e.target.value;
    });
  }

  getGallery(): void {
    this.galleryService
      .getGallery()
      .subscribe(
        data => (this.gallery = data)
      );
  }

  getCountries(): void {
    this.countryService
      .getCountries()
      .subscribe(
        data => (this.countries = data)
      );
  }


  update() : void {
    this.galleryService.updateGallery(this.gallery).pipe(first()).subscribe(
      data => {
        this.router.navigate(['/profile/gallery/view']);
      },
      error => {
        this.alertService.error(error);
      });
  }

  cancel() : void {
    this.router.navigate(['/profile/gallery/view']);
  }

}
