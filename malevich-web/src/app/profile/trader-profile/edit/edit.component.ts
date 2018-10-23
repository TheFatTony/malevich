import {AfterViewInit, Component, ElementRef, Inject, Input, LOCALE_ID, OnInit, ViewChild} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {CountryDto} from "../../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {TraderService} from "../../../_services/trader.service";
import {CountryService} from "../../../_services/country.service";
import {AuthService} from "../../../_services";
import {AddressDto} from "../../../_transfer/addressDto";
import {PersonDto} from "../../../_transfer";
import {GenderDto} from "../../../_transfer/genderDto";
import {GenderService} from "../../../_services/gender.service";
import {Router} from "@angular/router";

@Component({
  selector: 'trader-profile-security-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {

  trader: TraderDto;
  countries: CountryDto[];
  genders: GenderDto[];

  @Inject(LOCALE_ID) public locale: string;

  @ViewChild ('mobileInput') mobileInput: ElementRef;
  @ViewChild ('dateOfBirthInput') dateOfBirthInput: ElementRef;

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
    this.getCurrentTrader();
    this.getCountries();
    this.getGenders();
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSSelect.init('.js-custom-select');
    $['HSCore'].components.HSMaskedInput.init('[data-mask]');
    $['HSCore'].components.HSModalWindow.init('[data-modal-target]');
    $['HSCore'].components.HSDatepicker.init('#datepickerDefault');

    $(this.mobileInput.nativeElement).on('change', (e) => {
      this.trader.mobile = e.target.value;
    });

    $(this.dateOfBirthInput.nativeElement).on('change', (e) => {
      console.log(typeof e.target.constructor.name);
      this.trader.dateOfBirth = e.target.value;
    });
  }

  getCurrentTrader(): void {
    this.traderService
      .getTrader()
      .subscribe(
        data => {
          if (data)
            this.trader = data;
        }
      );
  }

  getCountries(): void {
    this.countryService
      .getCountries()
      .subscribe(
        data => (this.countries = data)
      );
  }

  getGenders(): void {
    this.genderService
      .getGenders()
      .subscribe(
        data => (this.genders = data)
      );
  }

  update() : void {
    this.trader.mobile = $('#mobile').val().toString();
    this.trader.dateOfBirth = new Date($('#datepickerDefault').val().toString().split('.').reverse().join('-'));
    this.traderService.update(this.trader);
    this.router.navigate(['/profile/trader/view'])
  }

}
