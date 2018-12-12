import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {AuthService} from "../../../_services";
import {Router} from "@angular/router";
import {SubscriptionService} from "../../../_services/subscription.service";
import {SubscriptionDto} from "../../../_transfer/subscriptionDto";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-auth-register-step-one',
  templateUrl: './step-one.component.html',
  styleUrls: ['./step-one.component.css']
})
export class StepOneComponent implements OnInit {


  email: string = "";
  agreementAccepted: boolean = false;
  subscribe: boolean = false;

  constructor(private router: Router,
              public translate: TranslateService,
              private authService: AuthService,
              private subscriptionService: SubscriptionService,
              private http: HttpClient) {
  }

  ngOnInit() {
    this.http.get('assets/i18n/agreement.en.html')
      .subscribe(resp => {
        console.log(resp);
      });
  }

  onSubmit() {
    this.authService.register(this.translate.currentLang, this.email)
      .subscribe();

    if (this.subscribe) {
      let subscription = new SubscriptionDto();
      subscription.emailId = this.email;
      this.subscriptionService.save(subscription).subscribe();
    }

    this.router.navigate(['/main-page']);
  }

}
