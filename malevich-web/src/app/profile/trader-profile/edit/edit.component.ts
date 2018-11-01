import {AfterViewInit, Component, ElementRef, Inject, Input, LOCALE_ID, OnInit, ViewChild} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {TranslateService} from "@ngx-translate/core";
import {TraderService} from "../../../_services/trader.service";
import {CountryService} from "../../../_services/country.service";
import {AuthService} from "../../../_services";
import {AddressDto} from "../../../_transfer/addressDto";
import {PersonDto} from "../../../_transfer";
import {GenderService} from "../../../_services/gender.service";
import {Router} from "@angular/router";

import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {jqxDateTimeInputComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxdatetimeinput';

@Component({
  selector: 'trader-profile-security-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {

  trader: TraderDto;
  countries: any[];
  genders: any[];

  @Inject(LOCALE_ID) public locale: string;

  @ViewChild('genderComboBox') genderComboBox: jqxComboBoxComponent;
  @ViewChild('countryComboBox') countryComboBox: jqxComboBoxComponent;
  @ViewChild('addressCountryComboBox') addressCountryComboBox: jqxComboBoxComponent;
  @ViewChild('dateOfBirthInput') dateOfBirthInput: jqxDateTimeInputComponent;

  constructor(private router: Router,
              public translate: TranslateService,
              private traderService: TraderService,
              private countryService: CountryService,
              private genderService: GenderService,
              private authService: AuthService) {
    this.trader = new TraderDto();
    this.trader.user = this.authService.getCurrentUser();
    this.trader.addresses = [new AddressDto()];
    this.trader.person = new PersonDto();
  }

  ngOnInit() {
    this.getCountries();
    this.getGenders();
    this.getCurrentTrader();
  }

  ngAfterViewInit(): void {
  }

  getCurrentTrader(): void {
    this.traderService
      .getTrader()
      .subscribe(
        data => {
          if (!data)
            return;

          this.trader = data;

          if (data.gender) {
            let genderIndex = this.genders.findIndex(value => value.value.id == data.gender.id);
            this.genderComboBox.selectIndex(genderIndex);
          }

          if (data.country) {
            let countryIndex = this.countries.findIndex(value => value.value.id == data.country.id);
            this.countryComboBox.selectIndex(countryIndex);
          }

          if (data.addresses && data.addresses.length > 0 && data.addresses[0].country) {
            let countryIndex = this.countries.findIndex(value => value.value.id == data.addresses[0].country.id);
            this.addressCountryComboBox.selectIndex(countryIndex);
          }
        }
      );
  }

  getCountries(): void {
    this.countryService
      .getCountries()
      .subscribe(data => (
        this.countries = data.map(i => ({
          title: i.nameMl[this.translate.currentLang],
          value: i
        }))
      ));
  }

  getGenders(): void {
    this.genderService
      .getGenders()
      .subscribe(data => (
        this.genders = data.map(i => ({
          title: i.nameMl[this.translate.currentLang],
          value: i
        }))
      ));
  }

  update(): void {
    this.trader.dateOfBirth = new Date(this.dateOfBirthInput.getText().split('/').reverse().join('-'));
    console.info(this.trader);
    this.traderService.update(this.trader);
    this.router.navigate(['/profile/trader/view'])
  }

  onDateOfBirthChange($event) {
    console.info($event);
    this.trader.dateOfBirth = new Date($event);
    console.info(this.trader);
  }
}
