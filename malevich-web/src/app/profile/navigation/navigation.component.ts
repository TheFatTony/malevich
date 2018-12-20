import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.dev";
import {CounterpartyService} from "../../_services/counterparty.service";
import {CounterpartyDto} from "../../_transfer/counterpartyDto";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-profile-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  counterparty: CounterpartyDto;
  isTrader: boolean = false;
  isGallery: boolean = false;

  titleName: string = "";

  public url = environment.baseUrl;

  constructor(private counterpartyService: CounterpartyService,
              private translate: TranslateService) {
  }

  ngOnInit() {
    this.getCurrentMember();
  }

  getCurrentMember(): void {
    this.counterpartyService
      .getCurrent()
      .subscribe(
        data => {
          data.user.roles.forEach(r => {
            this.isTrader = this.isTrader || (r == "TRADER");
            this.isGallery = this.isGallery || (r == "GALLERY");
          });

          this.counterparty = data;

          if (data.trader) {
            this.titleName = `${data.trader.person.firstName} ${data.trader.person.lastName}`
          } else if (data.gallery) {
            this.titleName = data.gallery.organization.legalNameMl[this.translate.currentLang]
              || data.gallery.organization.legalNameMl['en'];
          }
        }
      );
  }

}
