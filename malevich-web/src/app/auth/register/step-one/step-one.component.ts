import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";
import {SubscriptionService} from "../../../_services/subscription.service";
import {SubscriptionDto} from "../../../_transfer/subscriptionDto";
import {TermsAndConditionsService} from "../../../_services/terms-and-conditions.service";
import {UserService} from "../../../_services/user.service";
import {UserTypeService} from "../../../_services/user-type.service";
import {RegisterFormDto} from "../../../_transfer/registerFormDto";
import {AlertService, UserTypeDto} from '../../../../../node_modules/yinyang-core';

@Component({
  selector: 'app-auth-register-step-one',
  templateUrl: './step-one.component.html',
  styleUrls: ['./step-one.component.css']
})
export class StepOneComponent implements OnInit, AfterViewInit {

  email: string = "";
  agreementAccepted: boolean = false;
  subscribe: boolean = false;
  termsAndConditions: { [type: string]: string } = {};

  userTypes: UserTypeDto[];
  userTypeSelected: UserTypeDto;

  userTypeDisplayFunc = (type: UserTypeDto) => {
    return type.typeName;
  };

  constructor(private router: Router,
              public translate: TranslateService,
              private userService: UserService,
              private userTypeService: UserTypeService,
              private subscriptionService: SubscriptionService,
              private termsAndConditionsService: TermsAndConditionsService,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.getUserTypes();
    this.termsAndConditionsService.getHtml(this.translate.currentLang)
      .subscribe(data => {
        if (!data) return;

        data.forEach(i => {
          this.termsAndConditions[i.userType.typeName] = i.htmlText;
        });
      });
  }

  getUserTypes() {
    this.userTypeService.getAll()
      .subscribe(data => (this.userTypes = data.filter(t => t.typeName != 'Malevich')));
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSModalWindow.init('[data-modal-target]');
  }

  onSubmit() {
    let info = new RegisterFormDto();
    info.userName = this.email;
    info.userType = this.userTypeSelected;

    this.userService.register(this.translate.currentLang, info)
      .subscribe(() => {
        this.alertService.success(this.translate.instant('ALERT.REGISTER.MAIL_SENT', {email: info.userName}));
      });

    if (this.subscribe) {
      let subscription = new SubscriptionDto();
      subscription.emailId = this.email;
      this.subscriptionService.save(subscription).subscribe();
    }

    this.router.navigate(['/main-page']);
  }

}
