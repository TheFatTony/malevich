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
import {ParticipantService} from "../../_services/participant.service";
import {ParticipantDto} from "../../_transfer/participantDto";
import {UserDto} from '../../../../node_modules/yinyang-core';
import {Globals} from "../../globals";

@Component({
  selector: 'app-profile-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {

  participant: ParticipantDto;
  isGallery: boolean;

  user: UserDto;

  countries: any[];
  genders: any[];

  public url = environment.baseUrl;

  constructor(private router: Router,
              public translate: TranslateService,
              private participantService: ParticipantService,
              private countryService: CountryService,
              private genderService: GenderService,
              private globals: Globals) {
    this.globals.currentUser$.subscribe(data => {
      this.user = data
    });
  }

  ngOnInit() {
    this.initFields();
  }

  ngAfterViewInit(): void {
    // set button types to 'button' to avoid unattended form submit
    $('jqxfileupload').find('button').attr('type', 'button');
  }

  initFields() {
    // ensure trader is requested after countries and genders
    forkJoin(this.countryService.getCountries(), this.genderService.getGenders())
      .pipe(mergeMap(results => {
        this.countries = results[0];
        this.genders = results[1];
        return this.participantService.getCurrent();
      }))
      .pipe(map(data => {
        this.participant = this.participantService.initInstance(data);
        this.isGallery = this.participantService.isGallery(this.participant);
      }))
      .subscribe();
  }

  update(): void {
    if (this.participant.person) {
      this.participant.person.gender = this.participant.person.gender || null;
    }

    this.participant.addresses = this.participant.addresses.map(a => {
      a.country = a.country || null;
      return a;
    });

    if(this.participantService.isOrganization(this.participant))
      this.participant.country = this.participant.addresses[0].country;

    this.participantService.update(this.participant)
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
    this.participant.thumbnail = serverResponse;
  }

  countryDisplayFunc = (country: CountryDto) => {
    return country.nameMl[this.translate.currentLang];
  };

  genderDisplayFunc = (gender: GenderDto) => {
    return gender.nameMl[this.translate.currentLang];
  };
}
