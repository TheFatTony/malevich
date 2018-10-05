import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {TranslateService} from "@ngx-translate/core";
import {TraderService} from "../../../_services/trader.service";

@Component({
  selector: 'app-profile-trader-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent implements OnInit, AfterViewInit {

  isEditing: boolean = false;

  currentView: string = 'Security';
  trader: TraderDto;

  constructor(public translate: TranslateService, private traderService: TraderService) {
  }

  ngOnInit() {
    this.getCurrentTrader();
  }

  ngAfterViewChecked(): void {

  }

  switchMode() {
    this.isEditing = !this.isEditing;
  }

  getCurrentTrader(): void {
    this.traderService
      .getTrader()
      .subscribe(
        data => (this.trader = data)
      );
  }

}
