import {AfterViewInit, Component, ElementRef, Inject, Input, LOCALE_ID, OnInit, ViewChild} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {CountryDto} from "../../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {TraderService} from "../../../_services/trader.service";
import {CountryService} from "../../../_services/country.service";

@Component({
  selector: 'trader-profile-security-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {

  isEditing: boolean = false;
  trader: TraderDto;
  countries: CountryDto[];

  @Inject(LOCALE_ID) public locale: string;

  @ViewChild ('mobileInput') mobileInput: ElementRef;
  @ViewChild ('dateOfBirthInput') dateOfBirthInput: ElementRef;

  constructor(public translate: TranslateService,
              private traderService: TraderService,
              private countryService: CountryService) {
  }

  ngOnInit() {
    this.getCurrentTrader();
    this.getCountries();
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSMaskedInput.init('[data-mask]');
    $['HSCore'].components.HSModalWindow.init('[data-modal-target]');
    $['HSCore'].components.HSDatepicker.init('#datepickerDefault');
    $['HSCore'].components.HSSelect.init('.js-custom-select');

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
        data => (this.trader = data)
      );
  }

  getCountries(): void {
    this.countryService
      .getCountries()
      .subscribe(
        data => (this.countries = data)
      );
  }

  switchMode() {
    this.isEditing = !this.isEditing;
  }

  update() : void {
    this.trader.mobile = $('#mobile').val().toString();
    this.trader.dateOfBirth = new Date($('#datepickerDefault').val().toString().split('.').reverse().join('-'));
    this.traderService.update(this.trader);
    this.switchMode();
  }

}
