import {Component, OnInit} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {TraderService} from "../../../_services/trader.service";
import {environment} from "../../../../environments/environment.dev";

@Component({
  selector: 'app-trader-profile-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  trader: TraderDto;

  public url = environment.baseUrl;

  constructor(private traderService: TraderService) {
  }

  ngOnInit() {
    this.getCurrentTrader();
  }

  getCurrentTrader(): void {
    this.traderService
      .getTrader()
      .subscribe(
        data => (this.trader = data)
      );
  }

}
