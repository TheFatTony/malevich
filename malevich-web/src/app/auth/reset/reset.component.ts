import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {AlertService, AuthService} from "../../_services";

@Component({
  selector: 'app-auth-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.css']
})
export class ResetComponent implements OnInit {

  isSecondStep = false;
  activationCode: string;

  constructor(private route: ActivatedRoute,
              private alertService: AlertService) { }

  ngOnInit() {
    this.activationCode = this.route.snapshot.queryParams['token'];
    console.info("this.activationCode = " + this.activationCode);
    if (this.activationCode != undefined) {
      this.isSecondStep = true;
    }
  }

}
