import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {CountryService} from "../../_services/country.service";
import {AuthService} from "../../_services";
import {GenderService} from "../../_services/gender.service";
import {Router} from "@angular/router";

import {forkJoin} from "rxjs";
import {map, mergeMap} from "rxjs/operators";
import {environment} from "../../../environments/environment.dev";
import {CountryDto} from "../../_transfer/countryDto";
import {GenderDto} from "../../_transfer/genderDto";
import {CounterpartyService} from "../../_services/counterparty.service";
import {CounterpartyDto} from "../../_transfer/counterpartyDto";
import {GalleryDto, OrganizationDto, PersonDto} from "../../_transfer";
import {AddressDto} from "../../_transfer/addressDto";

@Component({
  selector: 'app-profile-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {

  counterparty : CounterpartyDto;
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
        return this.counterpartyService.getCurrent(); participant
      }))
      .pipe(map(data => {
        this.counterparty = this.counterpartyService.initInstance(data); participant
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
