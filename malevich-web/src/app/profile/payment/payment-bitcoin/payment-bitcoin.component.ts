import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {PaymentMethodDto} from "../../../_transfer/paymentMethodDto";
import {TranslateService} from "@ngx-translate/core";
import {PaymentMethodService} from "../../../_services/payment-method.service";

@Component({
  selector: 'app-profile-payment-bitcoin',
  templateUrl: './payment-bitcoin.component.html',
  styleUrls: ['./payment-bitcoin.component.css']
})
export class PaymentBitcoinComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;

  @Input('methods')
  set methods(list: PaymentMethodDto[]) {
    if (!list) return;
    this.addresses = list.filter(m => m.type.id == 'BTC');
  }

  @Output('onUpdate') onMethodUpdated = new EventEmitter<PaymentMethodDto>();

  addresses: PaymentMethodDto[];
  selectedRowIndex: number = -1;

  columns(names: any): any[] {
    return [
      {dataField: 'ADDRESS', text: names['PROFILE.GRID.ADDRESS'], width: '100%', columntype: 'textbox'},
    ];
  }

  constructor(private translate: TranslateService,
              private paymentMethodService: PaymentMethodService) {
    this.updateGrid();
  }

  ngOnInit() {
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
    this.paymentMethodService.generateBtc().subscribe(data => {
      this.onMethodUpdated.emit(data);
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
