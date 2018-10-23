import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {AlertService, AuthService} from "../../../_services";

@Component({
  selector: 'app-auth-reset-step-one',
  templateUrl: './step-one.component.html',
  styleUrls: ['./step-one.component.css']
})
export class StepOneComponent implements OnInit {

  email: string;

  constructor(private router: Router,
              public translate: TranslateService,
              private authService: AuthService,
              private alertService: AlertService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.authService.reset(this.translate.currentLang, this.email)
      .subscribe(
        data => {
          this.alertService.success(data);
        },
        error => {
          this.alertService.error(error);
        });

    this.router.navigate(['/main-page']);
  }

}
