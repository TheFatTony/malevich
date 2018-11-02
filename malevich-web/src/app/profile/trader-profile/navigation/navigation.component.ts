import {Component, OnInit} from '@angular/core';
import {TraderDto} from "../../../_transfer/traderDto";
import {TraderService} from "../../../_services/trader.service";

@Component({
  selector: 'app-trader-profile-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  trader: TraderDto;

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
