import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PaymentMethodDto} from "../../../../_transfer/paymentMethodDto";
import {CountryDto} from "../../../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {PaymentMethodAccountService} from "../../../../_services/payment-method-account.service";
import {CountryService} from "../../../../_services/country.service";

@Component({
  selector: 'app-profile-payment-account-edit',
  templateUrl: './payment-account-edit.component.html',
  styleUrls: ['./payment-account-edit.component.css']
})
export class PaymentAccountEditComponent implements OnInit {

  @Input()
  account: PaymentMethodDto = new PaymentMethodDto();

  @Output()
  onSubmit = new EventEmitter<PaymentMethodDto>();

  @Output()
  onCancel = new EventEmitter();

  countries: CountryDto[];

  countryDisplayFunc = (country: CountryDto) => {
    return country.nameMl[this.translate.currentLang];
  };

  constructor(private translate: TranslateService,
              private paymentMethodAccountService: PaymentMethodAccountService,
              private countryService: CountryService) {
  }

  getCountries() {
    this.countryService.getCountries().subscribe(data => {
      this.countries = data;
    });
  }

  ngOnInit() {
    this.getCountries();
  }

  save(){
    this.onSubmit.emit(this.account);
  }

  cancel(){
    this.onCancel.emit();
  }

}
