import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  Input,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {OrderService} from "../../../_services/order.service";
import {TranslateService} from "@ngx-translate/core";
import {TradeTypeService} from "../../../_services/trade-type.service";
import {TradeTypeDto} from "../../../_transfer/tradeTypeDto";
import {OrderDto} from "../../../_transfer/orderDto";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {jqxValidatorComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxvalidator";
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators
} from "@angular/forms";
import {ValidationService} from "../../../_services/validation.service";
import {DropDownListComponent} from "../../../core/components/dropdownlist.component";

@Component({
  selector: 'mch-order-window',
  templateUrl: './order-window.component.html',
  styleUrls: ['./order-window.component.css']
})
export class OrderWindowComponent implements OnInit, AfterViewInit {


  @Input('artworkStock') attrArtWorkStock: ArtworkStockDto;
  @Input('orderType') attrOrderType: string;
  @Output() onOrderPlaced = new EventEmitter();

  @ViewChild('divBody') divBody: ElementRef;
  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('myValidator') myValidator: jqxValidatorComponent;
  @ViewChild('tradeTypeDropDown') tradeTypeDropDown: DropDownListComponent;

  myForm: FormGroup;


  private x: number;
  private y: number;

  tradeTypes: TradeTypeDto [];
  tradeTypeDisplayFunc = (type: TradeTypeDto) => type.nameMl[this.translate.currentLang];
  expirationDateHidden: boolean = true;
  public newOrder: OrderDto = new OrderDto();

  private errMsgs: any = {
    amount: []
  }

  private translations: any = {
    amount: {
      required: 'Order amount is required',
      value: 'Value should be more than zero',
    }
  }

  constructor(private formBuilder: FormBuilder,
              public validationService: ValidationService,
              private orderService: OrderService,
              public translate: TranslateService,
              private tradeTypeService: TradeTypeService) {
  }

  ngOnInit() {
    this.getTradeTypes();

    this.myForm = this.formBuilder.group({
      amount: new FormControl(0, [
        Validators.required,
        this.amountPositiveValidator,
        this.amountTenValidator
      ]),
      tradeType: new FormControl('', Validators.required),
      expirationDate: new FormControl('', Validators.required)
    });

    this.myForm.valueChanges
      .subscribe(_ => this.checkFormValidity());


  }



  ngAfterViewInit(): void {
  }

  get controls() {
    return this.myForm.controls;
  }

  amountPositiveValidator(control: AbstractControl): ValidationErrors {
    let value = control.value;

    if (!value || value <= 0)
      return {positive: "Value should be greater then zero"}

    return null;
  }

  amountTenValidator(control: AbstractControl): ValidationErrors {
    let value = control.value;

    if (!value || value % 10 != 0)
      return {multipleOfTen: "Value should be a multiple of ten"}

    return null;
  }

  expirationDateSpecifiedValidator(control: AbstractControl): ValidationErrors {
    console.info(control.value);
    return null;
  }


  getTradeTypes(): void {
    this.tradeTypeService
      .getTradeTypes()
      .subscribe(
        data => {
          this.tradeTypes = data;
        }
      );
  }

  validationSuccess($event: any) {

    if (this.orderType().toLocaleLowerCase() == 'ask')
      this.orderService.placeAsk(this.newOrder).subscribe(() => {
        this.onOrderPlaced.emit(this.newOrder);
        this.myWindow.close();
      });

    if (this.orderType().toLocaleLowerCase() == 'bid')
      this.orderService.placeBid(this.newOrder).subscribe(() => {
        this.onOrderPlaced.emit(this.newOrder);
        this.myWindow.close();
      });
  }

  checkFormValidity(data?: any): void {
    console.log(this.controls);

    for (let k in this.errMsgs) {
      this.errMsgs[k] = [];
      if (this.myForm.controls[k].errors && this.myForm.controls[k].dirty) {
        for (let e in this.myForm.controls[k].errors) {
          if (this.translations[k][e]) {
            this.errMsgs[k].push(this.translations[k][e]);
          }
        }
      }
    }
  }

  showExpirationDateInput(show: boolean) {
    this.expirationDateHidden = !show;

    if (show)
      this.myWindow.height(300);
    else
      this.myWindow.height(240);
  }

  onTradeTypeSelected(event: any) {
    switch (event.args.item.value.id) {
      case "GTC_": {
        this.newOrder.expirationDate = null;
        this.showExpirationDateInput(false);

        break;
      }
      case "GTT0": {
        this.newOrder.expirationDate = new Date();
        this.showExpirationDateInput(false);
        break;
      }
      case "GTD_": {
        this.newOrder.expirationDate = new Date();
        this.showExpirationDateInput(true);
        break;
      }
    }
  }

  open() {
    this.newOrder.artworkStock = this.artworkStock();
    this.newOrder.expirationDate = null;
    this.newOrder.tradeType = this.tradeTypes[0];
    this.newOrder.amount = 0;

    this.controls.amount.setValue(-1);
    this.controls.tradeType.setValue(this.tradeTypes[0]);

    this.myWindow.width(310);
    this.myWindow.height(240);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  close() {
    this.myWindow.close();
  }

  sendButton() {
    this.myValidator.validate();
  }

  artworkStock(arg?: ArtworkStockDto): any {
    if (arg !== undefined) {
      this.attrArtWorkStock = arg;
    } else {
      return this.attrArtWorkStock;
    }
  }

  orderType(arg?: string): string {
    if (arg !== undefined) {
      this.attrOrderType = arg;
    } else {
      return this.attrOrderType;
    }
  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }
}
