import {AfterViewInit, Component} from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit {
  title = 'malevich-landing';

  ngAfterViewInit(): void {
// initialization of master slider


    // initialization of carousel
    // $['HSCore'].components.HSCarousel.init('.js-carousel');
    //
    // // initialization of header
    // $['HSCore'].components.HSHeader.init($('#js-header'));
    // $['HSCore'].helpers.HSHamburgers.init('.hamburger');
    //
    // // initialization of go to section
    // $ ['HSCore'].components.HSGoTo.init('.js-go-to');
    //
    // // initialization of rating
    // $['HSCore'].components.HSRating.init($('.js-rating'), {
    //   spacing: 2
    // });

    // var countdowns = $['HSCore'].components['HSCountdown'].init('.js-countdown', {
    //   yearsElSelector: '.js-cd-years',
    //   monthElSelector: '.js-cd-month',
    //   daysElSelector: '.js-cd-days',
    //   hoursElSelector: '.js-cd-hours',
    //   minutesElSelector: '.js-cd-minutes',
    //   secondsElSelector: '.js-cd-seconds'
    // });


  }

}
