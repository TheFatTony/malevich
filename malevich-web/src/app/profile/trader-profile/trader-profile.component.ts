import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TraderService} from "../../_services/trader.service";
import {TraderDto} from "../../_transfer/traderDto";

@Component({
  selector: 'app-profile-trader',
  templateUrl: './trader-profile.component.html',
  styleUrls: ['./trader-profile.component.css']
})
export class TraderProfileComponent implements OnInit, AfterViewInit {

  currentView: string = 'Security';

  trader: TraderDto;

  constructor(private traderService: TraderService) {
  }

  ngOnInit() {
    this.getCurrentTrader()
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
