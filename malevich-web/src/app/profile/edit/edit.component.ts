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
import {GalleryDto, UserDto} from "../../_transfer";
import {TraderDto} from "../../_transfer/traderDto";

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
              private authService: AuthService) {
    this.authService.getCurrentUser().subscribe(data => {
      this.user = data
    });
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
        return this.participantService.getCurrent();
      }))
      .pipe(map(data => {
        this.participant = this.participantService.initInstance(data);
        this.isGallery = this.participantService.isGallery(this.participant);
      }))
      .subscribe();
  }

  update(): void {
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
