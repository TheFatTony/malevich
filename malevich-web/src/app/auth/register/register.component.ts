import {AfterViewInit, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-auth-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, AfterViewInit {

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSSelect.init('.js-custom-select');
    var counters = $['HSCore'].components.HSCounter.init('[class*="js-counter"]');
  }

}
