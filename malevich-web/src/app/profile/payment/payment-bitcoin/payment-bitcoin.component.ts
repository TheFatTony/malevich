import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {PaymentMethodDto} from "../../../_transfer/paymentMethodDto";
import {TranslateService} from "@ngx-translate/core";
import {PaymentMethodBitcoinService} from "../../../_services/payment-method-bitcoin.service";

@Component({
  selector: 'app-profile-payment-bitcoin',
  templateUrl: './payment-bitcoin.component.html',
  styleUrls: ['./payment-bitcoin.component.css']
})
export class PaymentBitcoinComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;

  addresses: PaymentMethodDto[];
  selectedRowIndex: number = -1;

  columns(names: any): any[] {
    return [
      {dataField: 'ADDRESS', text: names['PROFILE.GRID.ADDRESS'], width: '100%', columntype: 'textbox'},
    ];
  }

  constructor(private translate: TranslateService,
              private paymentMethodBitcoinService: PaymentMethodBitcoinService) {
    this.updateGrid();
  }

  ngOnInit() {
    this.getMethods();
  }

  getMethods() {
    this.paymentMethodBitcoinService.getPaymentMethods().subscribe(data => {
      this.addresses = data;
    });
  }

  updateGrid() {
    this.translate
      .get([
        'PROFILE.GRID.ADDRESS'
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

  onAddButton() {
    this.paymentMethodBitcoinService.generateBtc().subscribe(data => {
      this.getMethods();
    });
  }

  onDeleteButton() {
    if (this.selectedRowIndex < 0)
      return;

    // this.editMethod = this.clone(this.cards[this.selectedRowIndex]);
    // this.openWindow();
    //todo delete
  }

}
