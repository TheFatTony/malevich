import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-profile-trader-security-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  @Input() trader;

  constructor() { }

  ngOnInit() {
  }

}
