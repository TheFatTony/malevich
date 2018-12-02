import {Component, OnInit} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {CountryDto} from "../../../_transfer/countryDto";
import {TraderService} from "../../../_services/trader.service";
import {TranslateService} from "@ngx-translate/core";
import {AuthService} from "../../../_services";
import {UserDto} from "../../../_transfer";
import {DelayedChangeService} from "../../../_services/delayed-change.service";
import {AlertService} from "yinyang-core";

@Component({
  selector: 'app-profile-trader-security-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  user: UserDto;
  trader: TraderDto;
  countries: CountryDto[];

  hasChanges: boolean

  constructor(public translate: TranslateService,
              private authService: AuthService,
              private traderService: TraderService,
              private delayedChangeService: DelayedChangeService,
              private alertService: AlertService) {
    this.authService.getCurrentUser().subscribe(data => {
      this.user = data
    });
  }

  ngOnInit() {
    this.getCurrentTrader();
  }

  ngAfterViewInit(): void {
  }

  findByTypeIdAndAndReferenceId(traderId: number): void {
    this.delayedChangeService
      .findByTypeIdAndAndReferenceId(traderId)
      .subscribe(
        data => {
          this.hasChanges = data;
          if (this.hasChanges === true)
            this.alertService.success("You have unprocessed changes.");
        }
      );
  }

  getCurrentTrader(): void {
    this.traderService
      .getTrader()
      .subscribe(
        data => {
          if (data) {
            this.trader = data;
            this.findByTypeIdAndAndReferenceId(this.trader.id);
          }
        }
      );
  }

}
