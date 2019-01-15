import {Component, OnInit} from '@angular/core';
import {CountryDto} from "../../_transfer/countryDto";
import {TranslateService} from "@ngx-translate/core";
import {AuthService} from "../../_services";
import {DelayedChangeService} from "../../_services/delayed-change.service";
import {UserDto} from "yinyang-core";
import {ParticipantService} from "../../_services/participant.service";
import {ParticipantDto} from "../../_transfer/participantDto";
import {Router} from "@angular/router";
import {UserService} from "../../_services/user.service";

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

  constructor(public translate: TranslateService,
              private authService: AuthService,
              private userService: UserService,
              private participantService: ParticipantService,
              private delayedChangeService: DelayedChangeService,
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
      .alertIfDelayedChanges("PARTICIPANT", participantId)
      .subscribe();
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
