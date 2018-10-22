import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {OrderDto} from "../../../_transfer/orderDto";
import {OrderService} from "../../../_services/order.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';

@Component({
  selector: 'app-profile-gallery-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit, AfterViewInit {

  orders: OrderDto[];

  @ViewChild('myGrid') myGrid: jqxGridComponent;

  public columns =
    [
      { text: 'Date', datafield: 'Date', columntype: 'textbox', width: '20%' },
      { text: 'Amount', datafield: 'Amount',columntype: 'textbox', width: '20%' },
      { text: 'Artwork', datafield: 'Artwork',columntype: 'textbox', width: '20%' },
      { text: 'Trade Type', datafield: 'Trade Type',columntype: 'textbox', width: '20%' },
      { text: 'Type', datafield: 'Type',columntype: 'textbox', width: '20%' }
    ];

  constructor(private orderService: OrderService, public translateService: TranslateService) {
  }

  ngAfterViewInit(): void {
    this.createButtons();
  }

  createButtonsContainers(statusbar: any): void {
    let buttonsContainer = document.createElement('div');
    buttonsContainer.style.cssText = 'overflow: hidden; position: relative; margin: 5px; height: 60px;';
    let addButtonContainer = document.createElement('div');
    addButtonContainer.id = 'addButton';
    addButtonContainer.style.cssText = 'float: left; margin-left: 5px;';
    buttonsContainer.appendChild(addButtonContainer);
    statusbar[0].appendChild(buttonsContainer);
  }

  createButtons(): void {
    let addButtonOptions = {
      width: 100, height: 25, value: 'Place an ask',
      imgSrc: 'https://www.jqwidgets.com/angular/images/add.png',
      imgPosition: 'center', textPosition: 'center',
      textImageRelation: 'imageBeforeText'
    }
    let addButton = jqwidgets.createInstance('#addButton', 'jqxButton', addButtonOptions);
    addButton.addEventHandler('click', (event: any): void => {
    });
  }



  ngOnInit() {
    this.getPlacedOrders();
  }

  getPlacedOrders(): void {
    this.orderService
      .getPlacedOrders()
      .subscribe(
        data => (this.orders = data)
      );
  }

}
