import {AfterViewInit, Component, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {PaymentsDto} from '../../_transfer/paymentsDto';
import {jqxWindowComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow';
import {PaymentsService} from '../../_services/payments.service';
import {AccountStateService} from '../../_services/account-state.service';
import {AccountStateDto} from '../../_transfer/accountStateDto';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {TranslateService} from '@ngx-translate/core';
import {PaymentMethodService} from "../../_services/payment-method.service";
import {PaymentMethodDto} from "../../_transfer/paymentMethodDto";
import {GenderDto} from "../../_transfer/genderDto";

@Component({
  selector: 'app-profile-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit, AfterViewInit, OnDestroy {

  // @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('withdrawWindow') withdrawWindow: jqxWindowComponent;
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  public newPayment: PaymentsDto;
  public newWithdraw: PaymentsDto;
  public accountState: AccountStateDto;
  payments: PaymentsDto[];

  x: number;
  y: number;
  private paymentMethods: PaymentMethodDto[];

  paymentMethodDisplayFunc = (paymMeth: PaymentMethodDto) => {
    switch (paymMeth.type.id) {
      case 'ACC':
        return `${paymMeth.bankName} ${paymMeth.iban}`
      default:
        return paymMeth.type.nameMl[this.translate.currentLang] + ' ' + paymMeth.id;
    }
  };


  constructor(private paymentsService: PaymentsService,
              private paymentMethodService: PaymentMethodService,
              private accountStateService: AccountStateService,
              public translate: TranslateService) {
  }

  ngOnInit() {
    this.getAccountState();
    this.getPayments();
    this.getPaymentMethods();
  }

  ngAfterViewInit(): void {
    this.updateGrid();
  }

  ngOnDestroy(): void {
    // this.myWindow.close();
    this.withdrawWindow.close();
  }

  getPaymentMethods() {
    this.paymentMethodService.getPaymentMethods()
      .subscribe(data => {
        this.paymentMethods = data;
      })
  }

  updateGrid() {
    this.translate
      .get([
        'PROFILE.GRID.PAYMENT_NO',
        'PROFILE.GRID.DATE',
        'PROFILE.GRID.AMOUNT',
        'PROFILE.GRID.TYPE',
        'PROFILE.GRID.PRINT'
      ])
      .subscribe(data => {
        this.myGrid.hideloadelement();
        this.myGrid.beginupdate();
        this.myGrid.setOptions
        ({
          columns: this.columns(data)
        });
        this.myGrid.endupdate();
      });
  }

  columns(names: any): any[] {
    return [
      {dataField: 'PAYMENT_NO', text: names['PROFILE.GRID.PAYMENT_NO'], width: '20%', columntype: 'textbox'},
      {dataField: 'DATE', text: names['PROFILE.GRID.DATE'], width: '20%', columntype: 'textbox'},
      {dataField: 'AMOUNT', text: names['PROFILE.GRID.AMOUNT'], width: '20%', columntype: 'textbox'},
      {dataField: 'TYPE', text: names['PROFILE.GRID.TYPE'], width: '20%', columntype: 'textbox'},
      {
        dataField: 'PRINT',
        text: names['PROFILE.GRID.PRINT'],
        width: '20%',
        columntype: 'button',
        cellsrenderer: (): string => {
          return names['PROFILE.GRID.PRINT'];
        },
        buttonclick: (row: number): void => {
          this.paymentsService.receiptPrint(this.myGrid.getrowdata(row).PAYMENT_NO).subscribe((data) => {
            let file = new Blob([data], {type: 'application/pdf'});
            let fileURL = URL.createObjectURL(file);
            window.open(fileURL);
          });
        }
      }
    ];
  }

  sendPayment() {
    // this.paymentsService.insert(this.newPayment).subscribe(() => {
    //   this.myWindow.close();
    //   this.getPayments();
    //   this.getAccountState();
    // });
  }

  sendWithdraw() {
    this.paymentsService.insert(this.newWithdraw).subscribe(() => {
      this.withdrawWindow.close();
      this.getPayments();
      this.getAccountState();
    });
  }

  openPaymentWindow() {
    // this.newPayment = new PaymentsDto();
    // this.myWindow.width(310);
    // this.myWindow.height(220);
    // this.myWindow.open();
    // this.myWindow.move(this.x, this.y);
  }

  openWithdrawWindow() {
    this.newWithdraw = new PaymentsDto();
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

  getAccountState(): void {
    this.accountStateService
      .getWallet()
      .subscribe(
        data => (this.accountState = data)
      );
  }

  getPayments(): void {
    this.paymentsService.getPayments().subscribe((data) => {
      this.payments = data;
    });
  }


}
