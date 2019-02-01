import {Component, EventEmitter, HostListener, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {PaymentMethodDto} from "../../../_transfer/paymentMethodDto";
import {TranslateService} from "@ngx-translate/core";
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {PaymentMethodService} from "../../../_services/payment-method.service";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";
import {PaymentMethodCardService} from "../../../_services/payment-method-card.service";

@Component({
  selector: 'app-profile-payment-card',
  templateUrl: './payment-card.component.html',
  styleUrls: ['./payment-card.component.css']
})
export class PaymentCardComponent implements OnInit, OnDestroy {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('myWindow') myWindow: jqxWindowComponent;

  cards: PaymentMethodDto[];
  selectedRowIndex: number = -1;

  editMethod: PaymentMethodDto = new PaymentMethodDto();

  x: number;
  y: number;

  columns(names: any): any[] {
    return [
      {dataField: 'PAN', text: names['PROFILE.GRID.PAN'], width: '40%', columntype: 'textbox'},
      {dataField: 'HOLDER', text: names['PROFILE.GRID.CARD_HOLDER'], width: '45%', columntype: 'textbox'},
      {dataField: 'EXPIRATION', text: names['PROFILE.GRID.CARD_EXPIRATION'], width: '15%', columntype: 'textbox'}
    ];
  }

  constructor(private translate: TranslateService,
              private paymentMethodCardService: PaymentMethodCardService) {
    this.updateGrid();
  }


  ngOnInit() {
    this.getMethods();
  }


  ngOnDestroy(): void {
    this.myWindow.close()
  }

  getMethods() {
    this.paymentMethodCardService.getPaymentMethods().subscribe(data => {
      this.cards = data;
    });
  }

  updateGrid() {
    this.translate
      .get([
        'PROFILE.GRID.PAN',
        'PROFILE.GRID.CARD_HOLDER',
        'PROFILE.GRID.CARD_EXPIRATION'
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
    this.myWindow.height(480);
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

    this.editMethod = this.clone(this.cards[this.selectedRowIndex]);
    this.openWindow();
  }

  save() {
    this.paymentMethodCardService.save(this.editMethod).subscribe(() => {
      this.myWindow.close();
      this.getMethods();
    });

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
