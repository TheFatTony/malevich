import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {TranslateService} from "@ngx-translate/core";
import {OrderDto} from "../../../../_transfer/orderDto";
import {OrderService} from "../../../../_services/order.service";

@Component({
  selector: 'app-orders-list',
  templateUrl: './orders-list.component.html',
  styleUrls: ['./orders-list.component.css']
})
export class OrdersListComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  orders: OrderDto[];

  constructor(private orderService: OrderService, public translate: TranslateService) {
    }

  getOrders(): void {
    this.orderService
      .getOrders()
      .subscribe(
        data => (this.orders = data)
      );
  }

  ngOnInit() {
    this.getOrders();
  }

}
