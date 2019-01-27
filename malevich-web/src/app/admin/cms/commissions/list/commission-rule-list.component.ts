import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {TranslateService} from '@ngx-translate/core';
import {Router} from "@angular/router";
import {CommissionRuleService} from "../../../../_services/commission-rule.service";
import {CommissionRuleDto} from "../../../../_transfer/commissionRuleDto";

@Component({
  selector: 'app-commission-rule-list',
  templateUrl: './commission-rule-list.component.html',
  styleUrls: ['./commission-rule-list.component.css']
})
export class CommissionRuleListComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  commissions: CommissionRuleDto[];

  selectedRowIndex: number = -1;


  constructor(private commissionRuleService: CommissionRuleService,
              private router: Router,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getCommissions();
  }

  getCommissions(): void {
    this.commissionRuleService
      .getAll()
      .subscribe(
        data => (this.commissions = data)
      );
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  onAddButton() {
    this.router.navigate(['/admin/cms/commissions/edit'], {
      queryParams: {
        "new": true
      }
    });
  }

  onEditButton() {
    if (this.selectedRowIndex < 0)
      return;

    let artist = this.commissions[this.selectedRowIndex];

    this.router.navigate(['/admin/cms/commissions/edit'], {
      queryParams: {
        "id": artist.id
      }
    });
  }
}
