import {Component, ElementRef, Inject, Input, LOCALE_ID, OnInit, ViewChild} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {CountryDto} from "../../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {TraderService} from "../../../_services/trader.service";
import {CountryService} from "../../../_services/country.service";
import {PersonDto, UserDto} from "../../../_transfer";
import {GenderDto} from "../../../_transfer/genderDto";
import {GenderService} from "../../../_services/gender.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-auth-register-step-two',
  templateUrl: './step-two.component.html',
  styleUrls: ['./step-two.component.css']
})
export class StepTwoComponent implements OnInit {

  trader: TraderDto;
  countries: CountryDto[];
  genders: GenderDto[];

  @Input() activationCode: string;

  @Inject(LOCALE_ID) public locale: string;

  @ViewChild('mobileInput') mobileInput: ElementRef;
  @ViewChild('dateOfBirthInput') dateOfBirthInput: ElementRef;

  constructor(private router: Router,
              public translate: TranslateService,
              private traderService: TraderService,
              private countryService: CountryService,
              private genderService: GenderService) {
    this.trader = new TraderDto();
    this.trader.country = new CountryDto();
    this.trader.person = new PersonDto();
    this.trader.gender = new GenderDto();
    this.trader.user = new UserDto();
  }

  ngOnInit() {
    this.getCountries();
    this.getGenders();
  }

  ngAfterViewInit(): void {
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

  submit(): void {
    this.trader.mobile = $('#mobile').val().toString();
    this.trader.dateOfBirth = new Date($('#datepickerDefault').val().toString().split('.').reverse().join('-'));
    this.traderService.create(this.trader, this.activationCode);
    this.router.navigate(['/profile/trader/view']);
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
}
