import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";
import {SubscriptionService} from "../../../_services/subscription.service";
import {SubscriptionDto} from "../../../_transfer/subscriptionDto";
import {TermsAndConditionsService} from "../../../_services/terms-and-conditions.service";
import {UserService} from "../../../_services/user.service";
import {UserTypeService} from "../../../_services/user-type.service";
import {RegisterFormDto} from "../../../_transfer/registerFormDto";
import {UserTypeDto} from '../../../../../node_modules/yinyang-core';

@Component({
  selector: 'app-auth-register-step-one',
  templateUrl: './step-one.component.html',
  styleUrls: ['./step-one.component.css']
})
export class StepOneComponent implements OnInit, AfterViewInit {

  email: string = "";
  agreementAccepted: boolean = false;
  subscribe: boolean = false;
  termsAndConditions: string;

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
              private termsAndConditionsService: TermsAndConditionsService) {
  }

  ngOnInit() {
    this.getUserTypes();
    this.termsAndConditionsService.getHtml(this.translate.currentLang)
      .subscribe(data => (this.termsAndConditions = data.htmlText));
  }

  getUserTypes(){
    this.userTypeService.getAll().subscribe(data => (this.userTypes = data));
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSModalWindow.init('[data-modal-target]');
  }

  onSubmit() {
    let info = new RegisterFormDto();
    info.userName = this.email;
    info.userType = this.userTypeSelected;

    this.userService.register(this.translate.currentLang, info)
      .subscribe();

    if (this.subscribe) {
      let subscription = new SubscriptionDto();
      subscription.emailId = this.email;
      this.subscriptionService.save(subscription).subscribe();
    }

    this.router.navigate(['/main-page']);
  }

}
