import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {TranslateService} from "@ngx-translate/core";
import {TraderService} from "../../../_services/trader.service";
import {CountryDto} from "../../../_transfer/countryDto";
import {CountryService} from "../../../_services/country.service";

@Component({
  selector: 'app-profile-trader-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent implements OnInit, AfterViewInit {

  isEditing: boolean = false;
  trader: TraderDto;
  countries: CountryDto[];

  @ViewChild ('someinput') someinput: ElementRef;

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
    $(this.someinput.nativeElement).on('change', (e) => {
      console.log('Change made -- ngAfterViewInit');
      this.onChange();
    });
  }

  onChange(): void{
    console.log('Change made -- onChange');
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

}
