import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  source: string[] =
    [
      'JavaScript Certification - Welcome to our network',
      'Business Challenges via Web take a part',
      'jQWidgets better web, less time. Take a tour',
      'Facebook - you have 7 new notifications',
      'Twitter - John Doe is following you. Look at his profile',
      'New videos, take a look at YouTube.com'
    ];

  constructor() {
    $.jqx.theme = 'metrodark';
  }

  ngOnInit() {
  }

}
