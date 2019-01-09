import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {UserService} from "../../../_services/user.service";

@Component({
  selector: 'app-auth-reset-step-one',
  templateUrl: './step-one.component.html',
  styleUrls: ['./step-one.component.css']
})
export class StepOneComponent implements OnInit {

  email: string;

  constructor(private router: Router,
              public translate: TranslateService,
              private userService: UserService) {
  }

  ngOnInit() {
  }

  onSubmit() {
    this.userService.reset(this.translate.currentLang, this.email)
      .subscribe(() => {
        this.router.navigate(['/main-page']);
      });


  }

}
