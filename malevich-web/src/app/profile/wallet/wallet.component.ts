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
import {ParameterService} from "../../_services/parameter.service";
import {PaymentMethodDepositReferenceService} from "../../_services/payment-method-deposit-reference.service";
import {ParticipantService} from "../../_services/participant.service";
import {KycLevelService} from "../../_services/kyc-level.service";

import { StripeService, StripeCardComponent, ElementOptions, ElementsOptions } from "ngx-stripe";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {environment} from "../../../environments/environment.dev";
import {MalevichStripeService} from "../../_services/malevich-stripe.service";
import {AlertService} from "yinyang-core";

type PaymentType = {
  value: string
  name: string
};

@Component({
  selector: 'app-profile-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('withdrawWindow') withdrawWindow: jqxWindowComponent;
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  public newPayment: PaymentsDto;
  public newWithdraw: PaymentsDto;
  public accountState: AccountStateDto;

  private referenceState: string;

  public amount: number = 10;

  payments: PaymentsDto[];
  paymentMethods: PaymentMethodDto[];
  cards: PaymentMethodDto[];
  withdrawMethods: PaymentMethodDto[];
  parameters: Map<string, string>;
  private kycLevels: Map<string, boolean>;
  hasWithdrawalAccess = false;

  paymentTypes: PaymentType[] = [
    {
      value: 'transfer',
      name: 'SEPA Transfer'
    },
    {
      value: 'saved_card',
      name: 'Saved Card'
    }
  ];
  selectedPaymentType: PaymentType;

  x: number;
  y: number;


  get reference() {
    if (this.referenceState)
      return this.referenceState;

    const ref = this.paymentMethods.filter(p => p.type.id == 'REF')[0];

    if (ref != null) {
      this.referenceState = ref.reference;
      return this.referenceState;
    }

    this.paymentMethodDepositReferenceService.get()
      .subscribe(data => {
        this.referenceState = data.reference;
      });

    return this.referenceState;
  }

  paymentTypeDisplayFunc = (paymType: PaymentType) => {
    return paymType.name;
  };

  paymentMethodDisplayFunc = (paymMeth: PaymentMethodDto) => {
    switch (paymMeth.type.id) {
      case 'ACC':
        return `${paymMeth.bankName} ${paymMeth.iban}`;
      case 'BTC':
        return `Bitcoin ${paymMeth.btcAddress}`;
      case 'CRD':
        return `Card ${paymMeth.cardNumber}`;
      default:
        return paymMeth.type.nameMl[this.translate.currentLang] + ' ' + paymMeth.id;
    }
  };


  @ViewChild(StripeCardComponent) card: StripeCardComponent;

  cardOptions: ElementOptions = {
    style: {
      base: {
        iconColor: '#666EE8',
        color: '#31325F',
        lineHeight: '40px',
        fontWeight: 300,
        fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
        fontSize: '18px',
        '::placeholder': {
          color: '#CFD7E0'
        }
      }
    }
  };

  elementsOptions: ElementsOptions = {
    locale: 'en'
  };

  stripeTest: FormGroup;


  constructor(private paymentsService: PaymentsService,
              private paymentMethodService: PaymentMethodService,
              private paymentMethodDepositReferenceService: PaymentMethodDepositReferenceService,
              private accountStateService: AccountStateService,
              private parameterService: ParameterService,
              private participantService: ParticipantService,
              private kycLevelService: KycLevelService,
              public translate: TranslateService,
              private fb: FormBuilder,
              private stripeService: StripeService,
              private malevichStripeService: MalevichStripeService,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.getAccountState();
    this.getPayments();
    this.getPaymentMethods();
    this.getParameters();
    this.getKycAccess();
    this.stripeService.setKey(environment.stripeKey);
    this.stripeTest = this.fb.group({
      name: ['', [Validators.required]]
    });
  }

  buy() {
    const name = this.stripeTest.get('name').value;
    this.stripeService
      .createToken(this.card.getCard(), { name })
      .subscribe(result => {
        if (result.token) {
          this.malevichStripeService.pay(result.token.id, this.amount).subscribe(()=>{
            this.myWindow.close();
            this.getAccountState();
            this.getPayments();
          });
        } else if (result.error) {
          this.alertService.error(result.error.message);
        }
      });
  }

  ngAfterViewInit(): void {
    this.updateGrid();
  }

  ngOnDestroy(): void {
    this.myWindow.close();
    this.withdrawWindow.close();
  }

  getKycAccess() {
    this.participantService.getCurrent()
      .subscribe(participant => {
        if (!participant)
          return;

        this.kycLevelService.getDetailing(participant.kycLevel.id)
          .subscribe(data => {
            this.kycLevels = data;
            this.hasWithdrawalAccess = this.hasKycAccess(['T_TIER2', 'G_TIER1']);
          });
      });
  }

  hasKycAccess(levels: string[]) {
    if (!levels) return true;
    console.log(this.kycLevels);
    if (!this.kycLevels)
      return false;

    for (let level of levels) {
      if (!!this.kycLevels[level])
        return true;
    }

    return false;
  }

  getPaymentMethods() {
    this.paymentMethodService.getPaymentMethods()
      .subscribe(data => {
        this.paymentMethods = data;
        this.withdrawMethods = this.paymentMethods.filter(p => p.type.id != 'REF');
        this.cards = this.paymentMethods.filter(p => p.type.id == 'CRD');
      })
  }

  getParameters() {
    this.parameterService.getParameters()
      .subscribe(data => {
        this.parameters = data;
      });
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
    this.paymentsService.insert(this.newPayment).subscribe(() => {
      this.myWindow.close();
      this.getPayments();
      this.getAccountState();
    });
  }

  sendWithdraw() {
    this.paymentsService.insert(this.newWithdraw).subscribe(() => {
      this.withdrawWindow.close();
      this.getPayments();
      this.getAccountState();
    });
  }

  openPaymentWindow() {
    this.newPayment = new PaymentsDto();
    this.myWindow.width(410);
    this.myWindow.height(550);
    this.myWindow.open();
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
