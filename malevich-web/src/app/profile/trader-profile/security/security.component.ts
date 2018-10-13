import {AfterViewInit, Component, ElementRef, Inject, Input, LOCALE_ID, OnInit, ViewChild} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {TranslateService} from "@ngx-translate/core";
import {TraderService} from "../../../_services/trader.service";
import {CountryDto} from "../../../_transfer/countryDto";

@Component({
  selector: 'app-profile-trader-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent implements OnInit, AfterViewInit {

  isEditing: boolean = false;
  @Input() trader: TraderDto;
  @Input() countries: CountryDto[];

  @Inject(LOCALE_ID) public locale: string;

  @ViewChild ('mobileInput') mobileInput: ElementRef;
  @ViewChild ('dateOfBirthInput') dateOfBirthInput: ElementRef;

  constructor(public translate: TranslateService,
              private traderService: TraderService) {
  }

  ngOnInit() {

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
