import {Component, OnInit} from '@angular/core';
import {CountryDto} from "../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {AuthService} from "../../_services";
import {GalleryDto, OrganizationDto, PersonDto, UserDto} from "../../_transfer";
import {DelayedChangeService} from "../../_services/delayed-change.service";
import {AlertService} from "yinyang-core";
import {ParticipantService} from "../../_services/participant.service";
import {ParticipantDto} from "../../_transfer/participantDto";
import {Router} from "@angular/router";
import {UserService} from "../../_services/user.service";
import {AddressDto} from "../../_transfer/addressDto";
import {TraderDto} from "../../_transfer/traderDto";

@Component({
  selector: 'app-profile-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  user: UserDto;
  participant: ParticipantDto;
  isGallery: boolean;
  countries: CountryDto[];

  changePassword = false;
  oldPassword: string;
  newPassword: string;

  hasChanges: boolean;

  constructor(public translate: TranslateService,
              private authService: AuthService,
              private userService: UserService,
              private participantService: ParticipantService,
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

  getCounterpartyDelayedChanges(participantId: number): void {
    this.delayedChangeService
      .getParticipantDelayedChanges(participantId)
      .subscribe(
        data => {
          this.hasChanges = data;
          if (this.hasChanges === true)
            this.alertService.success("You have unprocessed changes.");
        }
      );
  }

  getCurrent(): void {
    this.participantService
      .getCurrent()
      .subscribe(
        data => {
          if (data) {
            this.participant = this.participantService.initInstance(data);
            this.isGallery = this.participantService.isGallery(this.participant);

            this.getCounterpartyDelayedChanges(this.participant.id);
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
