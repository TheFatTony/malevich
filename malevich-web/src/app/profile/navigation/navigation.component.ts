import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.dev";
import {ParticipantService} from "../../_services/participant.service";
import {CounterpartyDto} from "../../_transfer/counterpartyDto";
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
          data.users[0].roles.forEach(r => {
            this.isTrader = this.isTrader || (r == "TRADER");
            this.isGallery = this.isGallery || (r == "GALLERY");
          });

          this.participant = data;
          this.traderPerson = data as TraderDto;
          this.gallery = data as GalleryDto;

          if (this.traderPerson && this.traderPerson.person) {
            this.titleName = `${this.traderPerson.person.firstName} ${this.traderPerson.person.lastName}`
          } else if (this.gallery && this.gallery.organization) {
            this.titleName = this.gallery.organization.legalNameMl[this.translate.currentLang] || this.gallery.organization.legalNameMl['en'];
          }
        }
      );
  }

}
