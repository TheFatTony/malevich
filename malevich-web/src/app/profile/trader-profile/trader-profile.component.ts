import {AfterViewInit, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-profile-trader',
  templateUrl: './trader-profile.component.html',
  styleUrls: ['./trader-profile.component.css']
})
export class TraderProfileComponent implements OnInit, AfterViewInit {

  currentView: string = 'Security';

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
  }

}
