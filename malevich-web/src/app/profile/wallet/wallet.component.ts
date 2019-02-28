import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  HostListener,
  OnDestroy,
  OnInit,
  ViewChild
} from '@angular/core';
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
import {NgForm} from "@angular/forms";

type PaymentType = {
  value: string
  name: string
};

declare var stripe: any;
declare var elements: any;

@Component({
  selector: 'app-profile-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('withdrawWindow') withdrawWindow: jqxWindowComponent;
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  @ViewChild('cardInfo') cardInfo: ElementRef;

  card: any;
  cardHandler = this.onChange.bind(this);
  error: string;



  public newPayment: PaymentsDto;
  public newWithdraw: PaymentsDto;
  public accountState: AccountStateDto;

  private referenceState: string;

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


  constructor(private paymentsService: PaymentsService,
              private paymentMethodService: PaymentMethodService,
              private paymentMethodDepositReferenceService: PaymentMethodDepositReferenceService,
              private accountStateService: AccountStateService,
              private parameterService: ParameterService,
              private participantService: ParticipantService,
              private kycLevelService: KycLevelService,
              public translate: TranslateService,
              private cd: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.getAccountState();
    this.getPayments();
    this.getPaymentMethods();
    this.getParameters();
    this.getKycAccess();
  }

  ngAfterViewInit(): void {
    this.updateGrid();

    this.card = elements.create('card');
    this.card.mount(this.cardInfo.nativeElement);

    this.card.addEventListener('change', this.cardHandler);

  }

  onChange({ error }) {
    if (error) {
      this.error = error.message;
    } else {
      this.error = null;
    }
    this.cd.detectChanges();
  }

  async onSubmit(form: NgForm) {
    const { token, error } = await stripe.createToken(this.card);

    if (error) {
      console.log('Something is wrong:', error);
    } else {
      console.log('Success!', token);
      // ...send the token to the your backend to process the charge
    }
  }

  ngOnDestroy(): void {
    this.myWindow.close();
    this.withdrawWindow.close();

    this.card.removeEventListener('change', this.cardHandler);
    this.card.destroy();
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
