import {Component, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {AccountStateDto} from "../../../_transfer/accountStateDto";
import {AccountStateService} from "../../../_services/account-state.service";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {PaymentsDto} from "../../../_transfer/paymentsDto";
import {PaymentsService} from "../../../_services/payments.service";

@Component({
  selector: 'app-wallets',
  templateUrl: './wallets.component.html',
  styleUrls: ['./wallets.component.css']
})
export class WalletsComponent implements OnInit, OnDestroy {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('withdrawWindow') withdrawWindow: jqxWindowComponent;

  x: number;
  y: number;

  wallets: AccountStateDto[];
  public newPayment: PaymentsDto;
  public newWithdraw: PaymentsDto;
  public selectedWallet: AccountStateDto;
  payments: PaymentsDto[];

  selectedRowIndex: number = -1;

  constructor(private accountStateService: AccountStateService,
              private paymentsService: PaymentsService,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getWallets();
  }

  ngOnDestroy(): void {
    this.myWindow.close();
    this.withdrawWindow.close();
  }

  getWallets(): void {
    this.accountStateService
      .getAllWallets()
      .subscribe(
        data => (this.wallets = data)
      );
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
    this.selectedWallet = this.wallets[this.selectedRowIndex];
  }

  sendPayment() {
    this.paymentsService.insert(this.newPayment).subscribe(() => {
      this.myWindow.close();
      this.getWallets();
    });
  }

  sendWithdraw() {
    this.paymentsService.insert(this.newWithdraw).subscribe(() => {
      this.withdrawWindow.close();
      this.getWallets();
    });
  }

  openPaymentWindow() {
    this.newPayment = new PaymentsDto();
    this.newPayment.participant = this.selectedWallet.participant;
    this.myWindow.width(310);
    this.myWindow.height(220);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  openWithdrawWindow() {
    this.newWithdraw = new PaymentsDto();
    this.newWithdraw.participant = this.selectedWallet.participant;
    this.withdrawWindow.width(310);
    this.withdrawWindow.height(220);
    this.withdrawWindow.open();
    this.withdrawWindow.move(this.x, this.y);
  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

  // nestedGrids: any[] = [];
  initRowDetails = (index: number, parentElement: any, gridElement: any, record: any): void => {
    let nestedGridContainer = parentElement.children[0];
    // this.nestedGrids[index] = nestedGridContainer;
    const wallet = this.wallets[record.boundindex];

    this.paymentsService.getPaymentsByParticipant(wallet.participant.id)
      .subscribe(data => {
        let paymentsSource = {
          datafields: [
            {name: 'id', type: 'number'},
            {name: 'effectiveDate', type: 'date'},
            {name: 'paymentMethodType', type: 'string'},
            {name: 'paymentType', type: 'string'},
            {name: 'amount', type: 'number'}
          ],
          localdata: data.map(p => ({
            id: p.id,
            effectiveDate: p.effectiveDate,
            paymentMethodType: p.paymentMethod ? p.paymentMethod.type.nameMl['en'] : '',
            paymentType: p.paymentType.nameMl['en'],
            amount: p.amount
          }))
        };
        let nestedGridAdapter = new jqx.dataAdapter(paymentsSource);
        if (nestedGridContainer != null) {
          let settings = {
            width: 780,
            height: 200,
            source: nestedGridAdapter,
            columns: [
              {text: 'Id', datafield: 'id', width: '10%'},
              {text: 'Effective Date', datafield: 'effectiveDate', width: '30%', cellsformat: 'S'},
              {text: 'Method Type', datafield: 'paymentMethodType', width: '20%'},
              {text: 'Payment Type', datafield: 'paymentType', width: '20%'},
              {text: 'Amount', datafield: 'amount', width: '20%'}
            ]
          };
          jqwidgets.createInstance(`#${nestedGridContainer.id}`, 'jqxGrid', settings);

        }
      });
  };

  rowDetailsTemplate: any = {
    rowdetails: '<div id="nestedGrid" style="margin: 10px;"></div>', rowdetailsheight: 220, rowdetailshidden: true
  };

  gridReady = (): void => {
    this.myGrid.showrowdetails(1);
  };

}
