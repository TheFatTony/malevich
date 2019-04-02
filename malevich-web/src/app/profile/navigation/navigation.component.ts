import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.dev";
import {ParticipantService} from "../../_services/participant.service";
import {TranslateService} from "@ngx-translate/core";
import {ParticipantDto} from "../../_transfer/participantDto";
import {KycLevelService} from "../../_services/kyc-level.service";
import {ActivatedRoute} from "@angular/router";
import {Globals} from "../../globals";

@Component({
  selector: 'app-profile-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  participant: ParticipantDto;

  isTrader: boolean = false;
  isGallery: boolean = false;

  kycLevels: Map<string, boolean>;
  routingKycLevels: { [name: string]: string[] } = {};

  titleName: string = "";

  public url = environment.baseUrl;

  constructor(private participantService: ParticipantService,
              private translate: TranslateService,
              private kycLevelService: KycLevelService,
              private route: ActivatedRoute,
              public globals: Globals) {
  }

  ngOnInit() {
    this.globals.isGallery$.subscribe(data => {
      this.isGallery = data;
    });

    this.globals.isTrader$.subscribe(data => {
      this.isTrader = data;
    });

    this.getRoutingKycLevels();
    this.getCurrentMember();
  }

  getRoutingKycLevels() {
    this.route.snapshot.parent.routeConfig.children
      .forEach(next => {
        if (next.data && next.data.kycLevels) {
          this.routingKycLevels[next.path] = next.data.kycLevels;
        }
      });
  }

  hasKycAccess(path: string) {
    const levels = this.routingKycLevels[path];

    if (!levels) return true;

    if (!this.kycLevels)
      return false;

    for (let level of levels) {
      if (!!this.kycLevels[level])
        return true;
    }

    return false;
  }

  getCurrentMember(): void {
    this.participantService
      .getCurrent()
      .subscribe(
        data => {
          if (!data)
            return;

          this.participant = this.participantService.initInstance(data);;

          this.kycLevelService.getDetailing(this.participant.kycLevel.id)
            .subscribe(kycData => {
              this.kycLevels = kycData;
            });

          // this.titleName = this.participantService.getName(this.participant) || this.participant.users[0].name;
          this.titleName = this.participantService.getName(this.participant) || "null";
        }
      );
  }

}
