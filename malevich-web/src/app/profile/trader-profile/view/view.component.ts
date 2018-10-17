import { Component, OnInit } from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {CountryDto} from "../../../_transfer/countryDto";
import {TraderService} from "../../../_services/trader.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-profile-trader-security-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  trader: TraderDto;
  countries: CountryDto[];

  constructor(public translate: TranslateService,
              private traderService: TraderService) {
  }

  ngOnInit() {
    this.getCurrentTrader();
  }

  ngAfterViewInit(): void {
  }

  getCurrentTrader(): void {
    this.traderService
      .getTrader()
      .subscribe(
        data => (this.trader = data)
      );
  }

}
