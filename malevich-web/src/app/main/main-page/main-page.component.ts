import {AfterViewInit, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit, AfterViewInit {


  constructor() {
  }

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
