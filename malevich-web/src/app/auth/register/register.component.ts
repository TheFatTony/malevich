import {AfterViewInit, Component, OnInit} from '@angular/core';
import {InvolvementService} from "../../_services/involvement.service";
import {InvolvementDto} from "../../_transfer";
import {AuthService} from "../../_services";
import {TranslateService} from "@ngx-translate/core";
import {ActivatedRoute} from "@angular/router";
import {AlertService} from "yinyang-core";

@Component({
  selector: 'app-auth-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, AfterViewInit {

  involvements: InvolvementDto;
  isSecondStep = false;
  activationCode: string;

  constructor(private involvementService: InvolvementService,
              private alertService: AlertService,
              public translate: TranslateService,
              private authService: AuthService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getInvolvementCounters();

    this.activationCode = this.route.snapshot.queryParams['token'];
    if (this.activationCode != undefined) {
      this.isSecondStep = true;
    }
  }

  ngAfterViewInit(): void {
    // $['HSCore'].components.HSSelect.init('.js-custom-select');
    var counters = $['HSCore'].components.HSCounter.init('[class*="js-counter"]');
    $['HSCore'].components.HSModalWindow.init('[data-modal-target]');
  }

  getInvolvementCounters(): void {
    this.involvementService
      .getInvolvementCounters()
      .subscribe(
        data => (this.involvements = data)
      );
  }

}
