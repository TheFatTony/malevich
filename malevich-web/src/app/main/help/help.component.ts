import {AfterViewInit, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-main-help',
  templateUrl: './help.component.html',
  styleUrls: ['./help.component.css'],
  host: {
    '(window:resize)': 'onResize($event)'
  }
})
export class HelpComponent implements OnInit, AfterViewInit {

  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSTabs.init('[role="tablist"]');
  }

  onResize(event) {
    setTimeout(function () {
      $['HSCore'].components.HSTabs.init('[role="tablist"]');
    }, 200);
  }

}
