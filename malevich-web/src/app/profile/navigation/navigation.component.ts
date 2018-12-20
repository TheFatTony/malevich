import {Component, OnInit} from '@angular/core';
import {TraderDto} from "../../_transfer/traderDto";
import {environment} from "../../../environments/environment.dev";
import {CounterpartyService} from "../../_services/counterparty.service";
import {CounterpartyDto} from "../../_transfer/counterpartyDto";

@Component({
  selector: 'app-profile-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  counterparty: CounterpartyDto;
  trader: TraderDto;

  public url = environment.baseUrl;

  constructor(private counterpartyService: CounterpartyService) {
  }

  ngOnInit() {
    this.getCurrentTrader();
  }

  getCurrentTrader(): void {
    this.counterpartyService
      .getCurrent()
      .subscribe(
        data => {
          this.counterparty = data;
          this.trader = data.trader;
        }
      );
  }

}
