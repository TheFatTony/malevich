import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {CountryService} from "../../../_services/country.service";
import {GalleryDto} from "../../../_transfer";
import {GalleryService} from "../../../_services/gallery.service";
import {Router} from "@angular/router";
import {first, map, mergeMap} from "rxjs/operators";
import {forkJoin} from "rxjs";
import {CountryDto} from "../../../_transfer/countryDto";

@Component({
  selector: 'app-profile-gallery-security-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {


  countries: CountryDto[];
  gallery: GalleryDto;

  countryDisplayFunc = (country: CountryDto) => {
    return country.nameMl[this.translate.currentLang]
  }

  constructor(public translate: TranslateService,
              private galleryService: GalleryService,
              private countryService: CountryService,
              private router: Router) {
  }

  ngOnInit() {
    this.initFields();
  }

  ngAfterViewInit(): void {

  }

  initFields() {
    // ensure gallery is requested after countries
    forkJoin(this.countryService.getCountries())
      .pipe(mergeMap(results => {
        this.countries = results[0];
        return this.galleryService.getGallery();
      }))
      .pipe(map(data => {
        if (!data)
          return;

        this.gallery = data;
      }))
      .subscribe();
  }

  update(): void {
    this.galleryService.updateGallery(this.gallery).pipe(first()).subscribe(
      data => {
        this.router.navigate(['/profile/gallery/view']);
      }
    );
  }

  cancel(): void {
    this.router.navigate(['/profile/gallery/view']);
  }
}
