import {AfterViewInit, Component, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {PaymentMethodDto} from "../../../_transfer/paymentMethodDto";
import {TranslateService} from "@ngx-translate/core";
import {PaymentMethodAccountService} from "../../../_services/payment-method-account.service";

@Component({
  selector: 'app-profile-payment-account',
  templateUrl: './payment-account.component.html',
  styleUrls: ['./payment-account.component.css']
})
export class PaymentAccountComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('myWindow') myWindow: jqxWindowComponent;

  accounts: PaymentMethodDto[];
  selectedRowIndex: number = -1;

  editMethod: PaymentMethodDto = new PaymentMethodDto();

  x: number;
  y: number;

  columns(names: any): any[] {
    return [
      {dataField: 'BANK_NAME', text: names['PROFILE.GRID.BANK_NAME'], width: '60%', columntype: 'textbox'},
      {dataField: 'IBAN', text: names['PROFILE.GRID.IBAN'], width: '40%', columntype: 'textbox'}
    ];
  }

  constructor(private translate: TranslateService,
              private paymentMethodAccountService: PaymentMethodAccountService) {
  }


  ngOnInit() {
    this.getMethods();
  }

  ngAfterViewInit(): void {
    this.updateGrid();
  }

  ngOnDestroy(): void {
    this.myWindow.close()
  }

  getMethods() {
    this.paymentMethodAccountService.getPaymentMethods().subscribe(data => {
      this.accounts = data;
    });
  }

  updateGrid() {
    this.translate
      .get([
        'PROFILE.GRID.BANK_NAME',
        'PROFILE.GRID.IBAN'
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

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  private openWindow() {
    this.myWindow.width(500);
    this.myWindow.height(700);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  onAddButton() {
    this.editMethod = new PaymentMethodDto();
    this.openWindow();
  }

  onEditButton() {
    if (this.selectedRowIndex < 0)
      return;

    this.editMethod = this.clone(this.accounts[this.selectedRowIndex]);
    this.openWindow();
  }

  save() {
    this.paymentMethodAccountService.save(this.editMethod).subscribe(() => {
      this.myWindow.close();
      this.getMethods();
    });

  }

  cancel() {
    this.myWindow.close();
  }

  private clone<T>(obj: T): T {
    return JSON.parse(JSON.stringify(obj));
  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

}
