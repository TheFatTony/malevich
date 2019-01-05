import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../../_services/user.service";

@Component({
  selector: 'app-auth-reset-step-two',
  templateUrl: './step-two.component.html',
  styleUrls: ['./step-two.component.css']
})
export class StepTwoComponent implements OnInit {

  password: string;
  passwordConfirm: string;

  @Input() token: string;

  constructor(private router: Router,
              private userService: UserService) {
  }

  ngOnInit() {
  }

  submit() {
    this.userService.setNewPassword(this.token, this.password).subscribe();
    this.router.navigate(['/main-page']);
  }

}
