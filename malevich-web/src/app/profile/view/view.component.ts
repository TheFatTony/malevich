import {Component, OnInit} from '@angular/core';
import {CountryDto} from "../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {AuthService} from "../../_services";
import {UserDto} from "../../_transfer";
import {DelayedChangeService} from "../../_services/delayed-change.service";
import {AlertService} from "yinyang-core";
import {CounterpartyService} from "../../_services/counterparty.service";
import {CounterpartyDto} from "../../_transfer/counterpartyDto";
import {Router} from "@angular/router";
import {UserService} from "../../_services/user.service";

@Component({
  selector: 'app-profile-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  user: UserDto;
  counterparty: CounterpartyDto;
  countries: CountryDto[];

  changePassword = false;
  oldPassword: string;
  newPassword: string;

  hasChanges: boolean

  constructor(public translate: TranslateService,
              private authService: AuthService,
              private userService: UserService,
              private counterpartyService: CounterpartyService,
              private delayedChangeService: DelayedChangeService,
              private alertService: AlertService,
              private router: Router) {
    this.authService.getCurrentUser().subscribe(data => {
      this.user = data
    });
  }

  ngOnInit() {
    this.getCurrent();
  }

  ngAfterViewInit(): void {
  }

  getCounterpartyDelayedChanges(counterpartyId: number): void {
    this.delayedChangeService
      .getCounterpartyDelayedChanges(counterpartyId)
      .subscribe(
        data => {
          this.hasChanges = data;
          if (this.hasChanges === true)
            this.alertService.success("You have unprocessed changes.");
        }
      );
  }

  getCurrent(): void {
    this.counterpartyService
      .getCurrent()
      .subscribe(
        data => {
          if (data) {
            this.counterparty = data;
            this.getCounterpartyDelayedChanges(this.counterparty.id);
          }
        }
      );
  }

  edit() {
    this.router.navigate(['/profile/edit']).then();
  }

  switchChangePassword() {
    this.changePassword = !this.changePassword;
  }

  onChangePassword() {
    this.userService.changePassword(this.oldPassword, this.newPassword).subscribe();
    this.switchChangePassword();
  }
}
