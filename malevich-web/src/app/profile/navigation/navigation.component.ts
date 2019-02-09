import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.dev";
import {ParticipantService} from "../../_services/participant.service";
import {TranslateService} from "@ngx-translate/core";
import {ParticipantDto} from "../../_transfer/participantDto";
import {KycLevelService} from "../../_services/kyc-level.service";
import {KycLevelDto} from "../../_transfer/kycLevelDto";

@Component({
  selector: 'app-profile-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  participant: ParticipantDto;

  isTrader: boolean = false;
  isGallery: boolean = false;

  kycLevels: KycLevelDto[];

  titleName: string = "";

  public url = environment.baseUrl;

  constructor(private participantService: ParticipantService,
              private translate: TranslateService,
              private kycLevelService: KycLevelService) {
  }

  ngOnInit() {
    this.getCurrentMember();
  }

  hasKycLevel(level: string) {
    if(!this.kycLevels)
      return false;

    if(!level) return false;

    return !!this.kycLevels.find(l => l.id == level);
  }

  getCurrentMember(): void {
    this.participantService
      .getCurrent()
      .subscribe(
        data => {
          if (!data)
            return;

          data.users[0].roles.forEach(r => {
            this.isTrader = this.isTrader || (r == "ROLE_TRADER");
            this.isGallery = this.isGallery || (r == "ROLE_GALLERY");
          });

          this.participant = data;

          this.kycLevelService.getDetailing(this.participant.kycLevel.id)
            .subscribe(kycData => {
              this.kycLevels = kycData;
            });

          if (this.participant.person) {
            this.titleName = `${this.participant.person.firstName} ${this.participant.person.lastName}`
          } else if (this.participant.organization) {
            this.titleName = this.participant.organization.legalNameMl[this.translate.currentLang] || this.participant.organization.legalNameMl['en'];
          }
        }
      );
  }

}
