import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
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
  isLegalPerson: boolean = false;
  isGallery: boolean = false;

  @Input() activationCode: string;

  constructor(private router: Router,
              private authService: AuthService,
              public translate: TranslateService) {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
  }

  submit(): void {
    this.authService
      .register2(this.activationCode, this.password)
      .subscribe();

    this.router.navigate(['/auth/login']);
  }


}
