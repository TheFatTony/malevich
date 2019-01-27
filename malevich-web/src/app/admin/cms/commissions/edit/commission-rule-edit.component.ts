import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {environment} from "../../../../../environments/environment.dev";
import {ActivatedRoute, Router} from "@angular/router";
import {CommissionRuleDto} from "../../../../_transfer/commissionRuleDto";
import {CommissionRuleService} from "../../../../_services/commission-rule.service";

@Component({
  selector: 'app-artists-edit',
  templateUrl: './commission-rule-edit.component.html',
  styleUrls: ['./commission-rule-edit.component.css']
})
export class CommissionRuleEditComponent implements OnInit {

  editCommission: CommissionRuleDto;

  isNew: boolean = false;

  public url = environment.baseUrl;

  constructor(private commissionRuleService: CommissionRuleService,
              private route: ActivatedRoute,
              private router: Router,
              public translate: TranslateService) {
  }

  ngOnInit() {
    let params = this.route.snapshot.queryParams;
    this.isNew = ("true" == params['new']);

    if (this.isNew) {
      this.editCommission = new CommissionRuleDto();
    } else {
      let id = params['id'];
      if (id != null)
        this.commissionRuleService.get(id).subscribe(data => {
          this.editCommission = data;
        })
    }

  }


  sendButton() {
    this.commissionRuleService.save(this.editCommission).subscribe(
      () => {
        this.router.navigate(['/admin/cms/commissions']);
      }
    );
  }
}
