import {AfterViewInit, Component} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Globals} from "./globals";
import {AuthService} from "./_services";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit{
  title = 'malevich-web';

  constructor(public translate: TranslateService, public globals: Globals, private loginService: AuthService) {
    translate.addLangs(['en', 'ru']);
    translate.setDefaultLang('en');

    const browserLang: string = translate.getBrowserLang();
    const lang: string = localStorage.getItem('currentLanguage');

    if (lang === null) {
      translate.use(browserLang.match(/en|ru/) ? browserLang : 'en');
    } else {
      translate.use(lang);
    }

    loginService.refreshToken();
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSGoTo.init('.js-go-to');
  }

  scrollToTop(event) {
    window.scroll(0, 0);
  }

}
