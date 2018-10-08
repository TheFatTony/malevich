import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile-trader',
  templateUrl: './trader-profile.component.html',
  styleUrls: ['./trader-profile.component.css']
})
export class TraderProfileComponent implements OnInit {

  currentView: string = 'Security';

  constructor() { }

  ngOnInit() {
  }

}
