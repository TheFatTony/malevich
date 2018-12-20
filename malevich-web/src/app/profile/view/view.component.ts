import {Component, OnInit} from '@angular/core';
import {TraderDto} from "../../_transfer/traderDto";
import {CountryDto} from "../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {AuthService} from "../../_services";
import {GalleryDto, UserDto} from "../../_transfer";
import {DelayedChangeService} from "../../_services/delayed-change.service";
import {AlertService} from "yinyang-core";
import {CounterpartyService} from "../../_services/counterparty.service";
import {CounterpartyDto} from "../../_transfer/counterpartyDto";

@Component({
  selector: 'app-profile-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  user: UserDto;
  counterparty: CounterpartyDto;
  trader: TraderDto;
  gallery: GalleryDto;
  countries: CountryDto[];

  changePassword = false;
  oldPassword: string;
  newPassword: string;

  hasChanges: boolean

  constructor(public translate: TranslateService,
              private authService: AuthService,
              private counterpartyService: CounterpartyService,
              private delayedChangeService: DelayedChangeService,
              private alertService: AlertService) {
    this.authService.getCurrentUser().subscribe(data => {
      this.user = data
    });
  }

  ngOnInit() {
    this.getCurrent();
  }

  ngAfterViewInit(): void {
  }

  findByTypeIdAndAndReferenceId(traderId: number): void {
    this.delayedChangeService
      .findByTypeIdAndAndReferenceId(traderId)
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
            this.trader = this.counterparty.trader;
            this.gallery = this.counterparty.gallery;
            this.findByTypeIdAndAndReferenceId(this.counterparty.id);
          }
        }
      );
  }

  switchChangePassword() {
    this.changePassword = !this.changePassword;
  }

  onChangePassword() {
    this.authService.changePassword(this.oldPassword, this.newPassword).subscribe();
    this.switchChangePassword();
  }
}
