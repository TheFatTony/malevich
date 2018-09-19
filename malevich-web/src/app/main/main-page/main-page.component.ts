import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit, AfterViewInit {

  objectKeys = { ru: 'Russian', en: 'English' };

  constructor(public translate: TranslateService) { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    var countdowns = $['HSCore'].components['HSCountdown'].init('.js-countdown', {
      yearsElSelector: '.js-cd-years',
      monthElSelector: '.js-cd-month',
      daysElSelector: '.js-cd-days',
      hoursElSelector: '.js-cd-hours',
      minutesElSelector: '.js-cd-minutes',
      secondsElSelector: '.js-cd-seconds'
    });
  }

}
