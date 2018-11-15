import {AfterViewInit, Component, Inject, LOCALE_ID, OnInit, ViewChild} from '@angular/core';
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
import {forkJoin} from "rxjs";
import {distinctUntilChanged, map, mergeMap} from "rxjs/operators";
import {environment} from "../../../../environments/environment.dev";

@Component({
  selector: 'trader-profile-security-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {

  trader: TraderDto;
  countries: any[];
  genders: any[];

  public url = environment.baseUrl;

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
  }

  ngOnInit() {
    this.trader = new TraderDto();
    this.authService.getCurrentUser().pipe(distinctUntilChanged()).subscribe(data => this.trader.user = data);
    this.trader.addresses = [new AddressDto()];
    this.trader.person = new PersonDto();
    this.initFields();
  }

  ngAfterViewInit(): void {
  }

  initFields() {
    // ensure trader is requested after countries and genders
    forkJoin(this.countryService.getCountries(), this.genderService.getGenders())
      .pipe(mergeMap(results => {
        this.countries = results[0].map(i => ({
          title: i.nameMl[this.translate.currentLang],
          value: i
        }));

        this.genders = results[1].map(i => ({
          title: i.nameMl[this.translate.currentLang],
          value: i
        }));

        return this.traderService.getTrader();
      }))
      .pipe(map(data => {
         this.trader = data;
      }))
      .subscribe();
  }

  update(): void {
    this.traderService.update(this.trader);
    this.router.navigate(['/profile/trader/view']);
  }

  onUploadEnd(event: any): void {
    let args = event.args;
    let serverResponce = args.response;
    this.trader.thumbnail = serverResponce;
  }


}
