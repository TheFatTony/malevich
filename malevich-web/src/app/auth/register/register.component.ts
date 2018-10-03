import {AfterViewInit, Component, OnInit} from '@angular/core';
import {InvolvementService} from "../../_services/involvement.service";
import {InvolvementDto} from "../../_transfer";

@Component({
  selector: 'app-auth-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, AfterViewInit {

  involvements: InvolvementDto;

  constructor(private involvementService: InvolvementService) {
  }

  ngOnInit() {
    this.getInvolvementCounters();
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSSelect.init('.js-custom-select');
    var counters = $['HSCore'].components.HSCounter.init('[class*="js-counter"]');
  }

  getInvolvementCounters(): void {
    this.involvementService
      .getInvolvementCounters()
      .subscribe(
        data => (this.involvements = data)
      );
  }

}
