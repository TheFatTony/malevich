import {Component, OnInit} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {CountryDto} from "../../../_transfer/countryDto";
import {TraderService} from "../../../_services/trader.service";
import {TranslateService} from "@ngx-translate/core";
import {AuthService} from "../../../_services";
import {UserDto} from "../../../_transfer";

@Component({
  selector: 'app-profile-trader-security-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  user: UserDto;
  trader: TraderDto;
  countries: CountryDto[];

  constructor(public translate: TranslateService,
              private authService: AuthService,
              private traderService: TraderService) {
    this.user = this.authService.getCurrentUser();
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
        data => {
          if (data)
            this.trader = data;
        }
      );
  }

}
