import {Component, Inject, Input, LOCALE_ID, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {UserDto} from "../../../_transfer";
import {Router} from "@angular/router";
import {AuthService} from "../../../_services";

@Component({
  selector: 'app-auth-register-step-two',
  templateUrl: './step-two.component.html',
  styleUrls: ['./step-two.component.css']
})
export class StepTwoComponent implements OnInit {

  password: string;
  passwordConfirm: string;

  @Input() activationCode: string;

  @Inject(LOCALE_ID) public locale: string;

  constructor(private router: Router,
              private authService: AuthService,
              public translate: TranslateService) {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
  }

  submit(): void {
    let user = new UserDto();
    user.password = this.password;

    this.authService
      .register2(this.activationCode, this.password)
      .subscribe();

    this.router.navigate(['/auth/login']);
  }


}
