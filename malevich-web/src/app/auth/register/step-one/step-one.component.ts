import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {AuthService} from "../../../_services";
import {Router} from "@angular/router";
import {SubscriptionService} from "../../../_services/subscription.service";
import {SubscriptionDto} from "../../../_transfer/subscriptionDto";
import {TermsAndConditionsService} from "../../../_services/terms-and-conditions.service";
import {UserService} from "../../../_services/user.service";

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

  constructor(private router: Router,
              public translate: TranslateService,
              private userService: UserService,
              private subscriptionService: SubscriptionService,
              private termsAndConditionsService: TermsAndConditionsService) {
  }

  ngOnInit() {
    this.termsAndConditionsService.getHtml(this.translate.currentLang)
      .subscribe(data => (this.termsAndConditions = data.htmlText));
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSModalWindow.init('[data-modal-target]');
  }

  onSubmit() {
    this.userService.register(this.translate.currentLang, this.email)
      .subscribe();

    if (this.subscribe) {
      let subscription = new SubscriptionDto();
      subscription.emailId = this.email;
      this.subscriptionService.save(subscription).subscribe();
    }

    this.router.navigate(['/main-page']);
  }

}
