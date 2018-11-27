import {AfterViewInit, Component, OnInit} from '@angular/core';
import {SubscriptionDto} from '../../_transfer/subscriptionDto';
import {SubscriptionService} from '../../_services/subscription.service';

@Component({
  selector: 'app-main-footer',
  templateUrl: './main-footer.component.html',
  styleUrls: ['./main-footer.component.css']
})
export class MainFooterComponent implements OnInit, AfterViewInit {

  subscribe: SubscriptionDto;

  constructor(private subscriptionService: SubscriptionService) {
  }

  ngOnInit() {
    this.subscribe = new SubscriptionDto();
  }

  ngAfterViewInit(): void {
  }

  submit(): void {
    this.subscriptionService.save(this.subscribe).subscribe(() => {
      this.subscribe.emailId = '';
    });
  }

}
