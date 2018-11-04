import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AlertService} from "../../_services";

@Component({
  selector: 'app-auth-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.css']
})
export class ResetComponent implements OnInit {

  isSecondStep = false;
  activationCode: string;

  constructor(private route: ActivatedRoute,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.activationCode = this.route.snapshot.queryParams['token'];
    if (this.activationCode != undefined) {
      this.isSecondStep = true;
    }
  }

}
