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
      {datafield: 'Id', text: 'Id', width: '25%', columntype: 'number'},
      {datafield: 'Date', text: 'Date', width: '25%', columntype: 'datetimeinput'},
      {datafield: 'Type', text: 'Type', width: '25%', columntype: 'textbox'},
      {datafield: 'Party', text: 'Party', width: '25%', columntype: 'textbox'},
      {datafield: 'Counterparty', text: 'Counterparty', width: '25%', columntype: 'textbox'},
      {datafield: 'Artwork', text: 'Artwork', width: '25%', columntype: 'textbox'},
      {datafield: 'Amount', text: 'Amount', width: '25%', columntype: 'number'},
      {datafield: 'Quantity', text: 'Quantity', width: '25%', columntype: 'number'}
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
