import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {TransactionsDto} from "../../../../_transfer/transactionsDto";
import {TransactionsService} from "../../../../_services/transactions.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-transactions-list',
  templateUrl: './transactions-list.component.html',
  styleUrls: ['./transactions-list.component.css']
})
export class TransactionsListComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  transactions: TransactionsDto[];

  columns: any[] =
    [
      {datafield: 'Id', width: '25%', columntype: 'number'},
      {datafield: 'Date', width: '25%', columntype: 'datetimeinput'},
      {datafield: 'Type', width: '25%', columntype: 'textbox'},
      {datafield: 'Party', width: '25%', columntype: 'textbox'},
      {datafield: 'Counterparty', width: '25%', columntype: 'textbox'},
      {datafield: 'Artwork', width: '25%', columntype: 'textbox'},
      {datafield: 'Amount', width: '25%', columntype: 'number'},
      {datafield: 'Quantity', width: '25%', columntype: 'number'}
    ];

  constructor(private transactionsService: TransactionsService, public translate: TranslateService) {
    $.jqx.theme = 'metrodark';
  }

  getTransactions(): void {
    this.transactionsService
      .getTransactions()
      .subscribe(
        data => (this.transactions = data)
      );
  }

  ngOnInit() {
    this.getTransactions();
  }

}
