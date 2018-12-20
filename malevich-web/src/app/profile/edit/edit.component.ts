import {AfterViewInit, Component, Inject, LOCALE_ID, OnInit} from '@angular/core';
import {TraderDto} from "../../_transfer/traderDto";
import {TranslateService} from "@ngx-translate/core";
import {CountryService} from "../../_services/country.service";
import {AuthService} from "../../_services";
import {AddressDto} from "../../_transfer/addressDto";
import {PersonDto} from "../../_transfer";
import {GenderService} from "../../_services/gender.service";
import {Router} from "@angular/router";

import {forkJoin} from "rxjs";
import {distinctUntilChanged, map, mergeMap} from "rxjs/operators";
import {environment} from "../../../environments/environment.dev";
import {CountryDto} from "../../_transfer/countryDto";
import {GenderDto} from "../../_transfer/genderDto";
import {CounterpartyService} from "../../_services/counterparty.service";
import {CounterpartyDto} from "../../_transfer/counterpartyDto";

@Component({
  selector: 'app-profile-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {

  counterparty : CounterpartyDto;
  trader: TraderDto;
  countries: any[];
  genders: any[];

  public url = environment.baseUrl;


  constructor(private router: Router,
              public translate: TranslateService,
              private counterpartyService: CounterpartyService,
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
        this.countries = results[0];
        this.genders = results[1];
        return this.counterpartyService.getCurrent();
      }))
      .pipe(map(data => {
        this.counterparty = data || new CounterpartyDto();
        this.trader = this.counterparty.trader || new TraderDto();
      }))
      .subscribe();
  }

  update(): void {
    this.counterpartyService.update(this.counterparty)
      .subscribe(data => this.router.navigate(['/profile/view']));
  }

  cancel(): void {
    this.router.navigate(['/profile/view']);
  }

  onUploadEnd(event: any): void {
    let args = event.args;
    let serverResponse = JSON.parse(args.response.toString()
      .replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">', '')
      .replace('<pre>', '')
      .replace('</pre>', ''));
    this.counterparty.image = serverResponse;
  }

  countryDisplayFunc = (country: CountryDto) => {
    return country.nameMl[this.translate.currentLang];
  }

  genderDisplayFunc = (gender: GenderDto) => {
    return gender.nameMl[this.translate.currentLang];
  }
}
