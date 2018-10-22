import {AfterViewInit, Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Globals} from "../../globals";
import {AuthService} from "../../_services";
import {Router} from "@angular/router";
import {UserDto} from "../../_transfer";

@Component({
  selector: 'app-main-header',
  templateUrl: './main-header.component.html',
  styleUrls: ['./main-header.component.css']
})
export class MainHeaderComponent implements OnInit, AfterViewInit {

  user: UserDto;
  constructor(
              public router: Router,
              public translate: TranslateService,
              public globals: Globals,
              public loginService: AuthService) {
  }

  ngOnInit() {
    this.user = this.loginService.getCurrentUser();
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
    this.loginService.logout();
    this.router.navigate(['/']);
  }

}
