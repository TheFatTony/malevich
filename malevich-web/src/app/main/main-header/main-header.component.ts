import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Globals} from "../../globals";
import {AuthService} from "../../_services";
import {Router} from "@angular/router";
import {UserDto} from '../../../../node_modules/yinyang-core';
import {ParticipantDto} from "../../_transfer/participantDto";
import {ParticipantService} from "../../_services/participant.service";
import {filter, filter} from "rxjs/operators";

@Component({
  selector: 'app-main-header',
  templateUrl: './main-header.component.html',
  styleUrls: ['./main-header.component.css']
})
export class MainHeaderComponent implements OnInit, AfterViewInit {

  user: UserDto;
  participant: ParticipantDto
  userName: string;
  isAdmin: boolean;

  constructor(
    public router: Router,
    public translate: TranslateService,
    public globals: Globals,
    public authService: AuthService,
    private participantService: ParticipantService) {
  }

  ngOnInit() {
    this.globals.currentUser$
      .pipe(filter((u) => !!u))
      .subscribe(data => {
        this.user = data;

        this.participantService.getCurrent()
          .subscribe(data => {
            this.participant = data;
            this.userName = this.participantService.getName(this.participant) || this.user.name;
          });
      });

    this.globals.isAdmin$.subscribe(data => {
      this.isAdmin = data;
    });
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSHeader.init($('#js-header'));
    $['HSCore'].helpers.HSHamburgers.init('.hamburger');

    $('.js-mega-menu')['HSMegaMenu']({
      event: 'hover',
      pageContainer: $('.container'),
      breakpoint: 991
    });

    $['HSCore'].components.HSDropdown.init($('[data-dropdown-target]'), {
      afterOpen: function () {
        $(this).find('input[type="search"]').focus();
      }
    });
  }

  changeLanguage(lang: string) {
    localStorage.setItem('currentLanguage', lang);
    this.translate.use(lang);
  }

  doLogout() {
    this.user = null;
    this.authService.logout();
    this.router.navigate(['/']);
  }

}
