import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TraderService} from "../../_services/trader.service";
import {TraderDto} from "../../_transfer/traderDto";
import {CountryService} from "../../_services/country.service";
import {CountryDto} from "../../_transfer/countryDto";

@Component({
  selector: 'app-profile-trader',
  templateUrl: './trader-profile.component.html',
  styleUrls: ['./trader-profile.component.css']
})
export class TraderProfileComponent implements OnInit, AfterViewInit {

  currentView: string = 'Security';

  trader: TraderDto;
  countries: CountryDto[];

  constructor(private traderService: TraderService,
              private countryService: CountryService) {
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
