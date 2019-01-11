import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.dev";
import {ParticipantService} from "../../_services/participant.service";
import {TranslateService} from "@ngx-translate/core";
import {ParticipantDto} from "../../_transfer/participantDto";
import {TraderDto} from "../../_transfer/traderDto";
import {GalleryDto} from "../../_transfer";

@Component({
  selector: 'app-profile-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  participant: ParticipantDto;
  traderPerson: TraderDto;
  gallery: GalleryDto;

  isTrader: boolean = false;
  isGallery: boolean = false;


  titleName: string = "";

  public url = environment.baseUrl;

  constructor(private participantService: ParticipantService,
              private translate: TranslateService) {
  }

  ngOnInit() {
    this.getCurrentMember();
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

          if (this.participant.person) {
            this.titleName = `${this.participant.person.firstName} ${this.participant.person.lastName}`
          } else if (this.participant.organization) {
            this.titleName = this.participant.organization.legalNameMl[this.translate.currentLang] || this.participant.organization.legalNameMl['en'];
          }
        }
      );
  }

}
