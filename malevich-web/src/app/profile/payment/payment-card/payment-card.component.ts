import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {PaymentMethodDto} from "../../../_transfer/paymentMethodDto";
import {TranslateService} from "@ngx-translate/core";
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {PaymentMethodService} from "../../../_services/payment-method.service";

@Component({
  selector: 'app-profile-payment-card',
  templateUrl: './payment-card.component.html',
  styleUrls: ['./payment-card.component.css']
})
export class PaymentCardComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;

  @Input('methods')
  set methods(list: PaymentMethodDto[]) {
    this.cards = list.filter(m => m.type.id == 'CRD');
  }

  cards: PaymentMethodDto[];
  selectedRowIndex: number = -1;

  editMethod: PaymentMethodDto = new PaymentMethodDto();

  columns(names: any): any[] {
    return [
      {dataField: 'PAN', text: names['PROFILE.GRID.PAN'], width: '40%', columntype: 'textbox'},
      {dataField: 'HOLDER', text: names['PROFILE.GRID.CARD_HOLDER'], width: '45%', columntype: 'textbox'},
      {dataField: 'EXPIRATION', text: names['PROFILE.GRID.CARD_EXPIRATION'], width: '15%', columntype: 'textbox'}
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

  onAddButton() {
    // this.router.navigate(['/admin/cms/artists/edit'], {
    //   queryParams: {
    //     "new": true
    //   }
    // });
  }

  onEditButton() {
    if (this.selectedRowIndex < 0)
      return;

    let card = this.cards[this.selectedRowIndex];

    // this.router.navigate(['/admin/cms/artists/edit'], {
    //   queryParams: {
    //     "id": artist.id
    //   }
    // });
  }

  save() {
    this.editMethod.type = {id:'CRD', nameMl:new Map<string, string>()};
    this.paymentMethodService.saveCard(this.editMethod).subscribe();
    this.editMethod = new PaymentMethodDto();
  }

}
