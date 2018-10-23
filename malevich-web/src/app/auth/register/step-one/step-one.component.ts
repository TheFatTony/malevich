import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {TranslateService} from "@ngx-translate/core";
import {AlertService, AuthService} from "../../../_services";
import {Router} from "@angular/router";

@Component({
  selector: 'app-auth-register-step-one',
  templateUrl: './step-one.component.html',
  styleUrls: ['./step-one.component.css']
})
export class StepOneComponent implements OnInit {


  loginForm: FormGroup;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              public translate: TranslateService,
              private authService: AuthService,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required]
    });
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.authService.register(this.translate.currentLang, this.f.email.value)
      .pipe(first())
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
