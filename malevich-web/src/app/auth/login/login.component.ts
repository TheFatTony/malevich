import {Component, OnInit} from '@angular/core';
import {AlertService, AuthService} from "../../_services";
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-auth-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  returnUrl: string;

  login: string = "";
  password: string = "";

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
              private alertService: AlertService) {
  }

  ngAfterViewInit() {
    $['HSCore'].components.HSSelect.init('.js-custom-select');
    $['HSCore'].components.HSGoTo.init('.js-go-to');
  }

  ngOnInit() {
    this.authService.logout();

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  onSubmit() {
    this.authService.login(this.login, this.password)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate([this.returnUrl]);
        });
  }

}
