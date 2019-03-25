import {AfterViewInit, Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {PaymentsDto} from '../../_transfer/paymentsDto';
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

import {ElementOptions, ElementsOptions, StripeCardComponent, StripeService} from "ngx-stripe";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {environment} from "../../../environments/environment.dev";
import {MalevichStripeService} from "../../_services/malevich-stripe.service";
import {AlertService} from "yinyang-core";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {PaymentMethodBitcoinService} from "../../_services/payment-method-bitcoin.service";
import {BalanceHistoryDto} from "../../_transfer/balabceHistoryDto";

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

  @ViewChild('myWindow') myWindow: TemplateRef<any>;
  @ViewChild('addAccountModal') addAccountWindow: TemplateRef<any>;
  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('myGrid1') myGrid1: jqxGridComponent;

  public newPayment: PaymentsDto;
  public accountState: AccountStateDto;

  private referenceState: string;
  private paymentModalRef: NgbModalRef;
  private addAccountModalRef: NgbModalRef;
  private depositWithdraw: 'deposit' | 'withdraw';

  public amount: number = 0;

  payments: PaymentsDto[];
  payments1: BalanceHistoryDto[];

  paymentMethods: PaymentMethodDto[];
  cards: PaymentMethodDto[];
  withdrawMethods: PaymentMethodDto[];
  parameters: Map<string, string>;
  private kycLevels: Map<string, boolean>;
  hasWithdrawalAccess = false;

  gridSource: any;
  gridSource1: any;

  paymentTypes: PaymentType[] = [
    {
      value: 'swift',
      name: 'Wire transfer'
    },
    {
      value: 'saved_card',
      name: 'Card'
    },
    {
      value: 'bitcoin',
      name: 'Bitcoin'
    }
  ];
  selectedDepositType: PaymentType;

  addresses: PaymentMethodDto[];

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
              private alertService: AlertService,
              private modalService: NgbModal,
              private paymentMethodBitcoinService: PaymentMethodBitcoinService) {
  }

  ngOnInit() {
    this.getAccountState();
    this.getPayments();
    this.getPayments1();
    this.getPaymentMethods();
    this.getParameters();
    this.getKycAccess();
    this.getMethods();


    this.stripeService.setKey(environment.stripeKey);
    this.stripeTest = this.fb.group({
      name: ['', [Validators.required]]
    });
  }

  getMethods() {
    this.paymentMethodBitcoinService.getPaymentMethods().subscribe(data => {
      this.addresses = data;

      if ((this.addresses == null) || (this.addresses.length === 0)) {
        this.onAddButton();
      }
    });
  }

  onAddButton() {
    this.paymentMethodBitcoinService.generateBtc().subscribe(data => {
      this.getMethods();
    });
  }

  buy() {
    const name = this.stripeTest.get('name').value;
    this.stripeService
      .createToken(this.card.getCard(), {name})
      .subscribe(result => {
        if (result.token) {
          this.malevichStripeService.pay(result.token.id, this.amount).subscribe(() => {
            this.paymentModalRef.close();
            this.getAccountState();
            this.getPayments();
            this.getPayments1();
          });
        } else if (result.error) {
          this.alertService.error(result.error.message);
        }
      });
  }

  ngAfterViewInit(): void {
    this.updateGrid();
    this.updateGrid1();
  }

  ngOnDestroy(): void {
    this.modalService.dismissAll();
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

  updateGrid1() {
    this.translate
      .get([
        'PROFILE.GRID.PAYMENT_NO',
        'PROFILE.GRID.DATE',
        'PROFILE.GRID.AMOUNT',
        'PROFILE.GRID.TYPE',
      ])
      .subscribe(data => {
        this.myGrid1.hideloadelement();
        this.myGrid1.beginupdate();
        this.myGrid1.setOptions
        ({
          columns: this.columns1(data)
        });

        this.myGrid1.endupdate();
      });
  }

  columns1(names: any): any[] {
    return [
      {dataField: 'id', text: names['PROFILE.GRID.PAYMENT_NO'], width: '25%', columntype: 'textbox'},
      {
        dataField: 'effectiveDate',
        text: names['PROFILE.GRID.DATE'],
        width: '25%',
        cellsformat: 'dd/MM/yyyy HH:mm:ss'
      },
      {dataField: 'amount', text: names['PROFILE.GRID.AMOUNT'], width: '25%', cellsformat: 'd'},
      {dataField: 'operationType', text: names['PROFILE.GRID.TYPE'], width: '25%', columntype: 'textbox'}
    ];
  }

  columns(names: any): any[] {
    return [
      {dataField: 'id', text: names['PROFILE.GRID.PAYMENT_NO'], width: '20%', columntype: 'textbox'},
      {
        dataField: 'effectiveDate',
        text: names['PROFILE.GRID.DATE'],
        width: '20%',
        cellsformat: 'dd/MM/yyyy HH:mm:ss'
      },
      {dataField: 'amount', text: names['PROFILE.GRID.AMOUNT'], width: '20%', cellsformat: 'd'},
      {dataField: 'paymentType', text: names['PROFILE.GRID.TYPE'], width: '20%', columntype: 'textbox'},
      {
        text: names['PROFILE.GRID.PRINT'],
        width: '20%',
        columntype: 'button',
        cellsrenderer: (): string => {
          return names['PROFILE.GRID.PRINT'];
        },
        buttonclick: (row: number): void => {
          this.paymentsService.receiptPrint(this.myGrid.getrowdata(row).id).subscribe((data) => {
            let file = new Blob([data], {type: 'application/pdf'});
            let fileURL = URL.createObjectURL(file);
            window.open(fileURL);
          });
        }
      }
    ];
  }

  sendWithdraw() {
    this.paymentsService.insert(this.newPayment).subscribe(() => {
      this.closeWindow();
      this.getPayments();
      this.getAccountState();
    });
  }

  openDepositWindow() {
    this.depositWithdraw = 'deposit';
    this.openPaymentWindow();
  }

  openWithdrawWindow() {
    this.depositWithdraw = 'withdraw';
    this.openPaymentWindow();
  }

  private openPaymentWindow() {
    this.selectedDepositType = null;
    this.newPayment = new PaymentsDto();
    this.paymentModalRef = this.modalService.open(this.myWindow, {centered: true});
  }

  closeWindow() {
    if (this.paymentModalRef)
      this.paymentModalRef.close();
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

      this.gridSource = {
        datatype: "array",
        datafields: [
          { name: 'id', type: 'number' },
          { name: 'effectiveDate', type: 'date' },
          { name: 'amount', type: 'number' },
          { name: 'paymentType', type: 'string' },
        ],
        localdata: data.map(i => ({
          id: i.id,
          effectiveDate: i.effectiveDate,
          amount: i.amount,
          paymentType: i.paymentType.nameMl[this.translate.currentLang]
        }))
      };

    });
  }

  getPayments1(): void {
    this.paymentsService.getPayments1().subscribe((data) => {
      this.payments1 = data;

      this.gridSource1 = {
        datatype: "array",
        datafields: [
          { name: 'id', type: 'number' },
          { name: 'effectiveDate', type: 'date' },
          { name: 'amount', type: 'number' },
          { name: 'operationType', type: 'string' },
        ],
        localdata: data.map(i => ({
          id: i.id,
          effectiveDate: i.effectiveDate,
          amount: i.amount,
          operationType: i.operationType
        }))
      };

    });
  }


  addAccount() {
    this.addAccountModalRef = this.modalService.open(this.addAccountWindow, {centered: true});
  }

  accountCreated(account: PaymentMethodDto) {
    this.getPaymentMethods();
    this.newPayment.paymentMethod = account;
    this.addAccountModalRef.close();
  }

  accountCreationCanceled() {
    this.addAccountModalRef.close();
  }
}
