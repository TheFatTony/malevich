import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {CountryService} from "../../../_services/country.service";
import {GalleryDto} from "../../../_transfer";
import {GalleryService} from "../../../_services/gallery.service";
import {Router} from "@angular/router";
import {AlertService} from "../../../_services";
import {first, map, mergeMap} from "rxjs/operators";
import {forkJoin} from "rxjs";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";

@Component({
  selector: 'app-profile-gallery-security-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {


  countries: any[];
  gallery: GalleryDto;

  addressCountryComboBox: jqxComboBoxComponent;
  selectedAddressIndex: number;

  @ViewChild('addressCountryComboBox2') set content(content: jqxComboBoxComponent) {
    this.addressCountryComboBox = content;
  }

  constructor(public translate: TranslateService,
              private galleryService: GalleryService,
              private countryService: CountryService,
              private router: Router,
              private alertService: AlertService) {
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
        this.countries = results[0].map(i => ({
          title: i.nameMl[this.translate.currentLang],
          value: i
        }));

        return this.galleryService.getGallery();
      }))
      .pipe(map(data => {
        if (!data)
          return;

        this.gallery = data;

        if (data.addresses && data.addresses.length > 0 && data.addresses[0].country) {
          this.selectedAddressIndex = this.countries.findIndex(value => value.value.id == data.addresses[0].country.id);
          //console.info(this.addressCountryComboBox);
          //this.addressCountryComboBox.selectIndex(countryIndex);
        }
      }))
      .subscribe();
  }

  update(): void {
    this.galleryService.updateGallery(this.gallery).pipe(first()).subscribe(
      data => {
        this.router.navigate(['/profile/gallery/view']);
      },
      error => {
        this.alertService.error(error);
      });
  }

  cancel(): void {
    this.router.navigate(['/profile/gallery/view']);
  }
}
