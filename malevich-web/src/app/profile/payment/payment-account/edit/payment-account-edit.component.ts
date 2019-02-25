import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PaymentMethodDto} from "../../../../_transfer/paymentMethodDto";
import {CountryDto} from "../../../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {PaymentMethodAccountService} from "../../../../_services/payment-method-account.service";
import {CountryService} from "../../../../_services/country.service";
import {ParticipantService} from "../../../../_services/participant.service";

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
  editAccount: PaymentMethodDto = new PaymentMethodDto();

  countryDisplayFunc = (country: CountryDto) => {
    return country.nameMl[this.translate.currentLang];
  };

  constructor(private translate: TranslateService,
              private paymentMethodAccountService: PaymentMethodAccountService,
              private countryService: CountryService,
              private participantService: ParticipantService) {
  }

  getCountries() {
    this.countryService.getCountries().subscribe(data => {
      this.countries = data;
      this.editAccount = this.account;
    });
  }

  getParticipant(){
    this.participantService.getCurrent()
      .subscribe(data =>{
        if(!data) return;

        if(!this.account.beneficiaryName){
          if(data.person){
            this.account.beneficiaryName = `${data.person.firstName} ${data.person.lastName}`;
          } else if(data.organization){
            this.account.beneficiaryName = data.organization.legalNameMl['en'];
          }
        }
      });
  }

  ngOnInit() {
    this.getCountries();
    this.getParticipant();
  }

  save() {
    this.paymentMethodAccountService.save(this.editAccount).subscribe(() => {
      this.onSubmit.emit(this.editAccount);
    });
  }

  cancel() {
    this.onCancel.emit();
  }

}
