import {AfterViewInit, Component, ElementRef, Inject, Input, LOCALE_ID, OnInit, ViewChild} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {TranslateService} from "@ngx-translate/core";
import {TraderService} from "../../../_services/trader.service";
import {CountryDto} from "../../../_transfer/countryDto";
import {CountryService} from "../../../_services/country.service";
import {AddressService} from "../../../_services/address.service";

@Component({
  selector: 'app-profile-trader-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent implements OnInit, AfterViewInit {

  creation: boolean = false;
  accountEditing: boolean = false;
  tier0Editing: boolean = false;
  tier1Editing: boolean = false;


  @Input() trader: TraderDto;
  countries: CountryDto[];

  @Inject(LOCALE_ID) public locale: string

  @ViewChild ('mobileInput') mobileInput: ElementRef;
  @ViewChild ('dateOfBirthInput') dateOfBirthInput: ElementRef;

  constructor(public translate: TranslateService,
              private traderService: TraderService,
              private countryService: CountryService,
              private addressService: AddressService) {
  }

  ngOnInit() {
    this.getCountries();
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

  switchAccountMode() {
    this.accountEditing = !this.accountEditing;
  }

  switchTier0Mode() {
    this.tier0Editing = !this.tier0Editing;
  }

  switchTier1Mode() {
    this.tier1Editing = !this.tier1Editing;
  }

  private update(){
    this.traderService.update(this.trader);
  }

  updateAccount() : void {
    this.update();
    this.switchAccountMode();
  }

  updateTier0() : void {
    this.trader.mobile = $('#mobile').val().toString();
    this.trader.dateOfBirth = new Date($('#datepickerDefault').val().toString().split('.').reverse().join('-'));
    this.update();
    this.switchTier0Mode();
  }

  updateTier1() : void {
    this.update();
    this.switchTier1Mode()
  }

  getCountries(): void {
    this.countryService
      .getCountries()
      .subscribe(
        data => (this.countries = data)
      );
  }

}
