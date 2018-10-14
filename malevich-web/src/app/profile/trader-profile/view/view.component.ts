import { Component, OnInit } from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {CountryDto} from "../../../_transfer/countryDto";
import {ActivatedRoute, Params} from "@angular/router";
import {TraderService} from "../../../_services/trader.service";
import {CountryService} from "../../../_services/country.service";

@Component({
  selector: 'app-profile-trader-security-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  currentView: string = 'security';

  trader: TraderDto;
  countries: CountryDto[];

  constructor(private route: ActivatedRoute,
              private traderService: TraderService,
              private countryService: CountryService) {
    this.route.params.forEach((params: Params) => {
      if(params['view']) {
        this.currentView = params['view'];
      }
    });
  }

  ngOnInit() {
    this.getCurrentTrader();
    this.getCountries();
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

  getCountries(): void {
    this.countryService
      .getCountries()
      .subscribe(
        data => (this.countries = data)
      );
  }

}
