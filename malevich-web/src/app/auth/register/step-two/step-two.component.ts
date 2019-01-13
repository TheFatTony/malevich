import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";
import {UserService} from "../../../_services/user.service";

@Component({
  selector: 'app-auth-register-step-two',
  templateUrl: './step-two.component.html',
  styleUrls: ['./step-two.component.css']
})
export class StepTwoComponent implements OnInit {

  password: string;
  passwordConfirm: string;

  @Input() activationCode: string;

  constructor(private router: Router,
              private userService: UserService,
              public translate: TranslateService) {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
  }

  submit(): void {
    this.userService
      .register2(this.activationCode, {
        password: this.password
      })
      .subscribe();

    this.router.navigate(['/auth/login']);
  }


}
