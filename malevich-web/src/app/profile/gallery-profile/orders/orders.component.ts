import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {OrderDto} from "../../../_transfer/orderDto";
import {OrderService} from "../../../_services/order.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-profile-gallery-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit, AfterViewInit {

  orders: OrderDto[];

  public url = environment.baseUrl;

  constructor(public orderService: OrderService, public translate: TranslateService) {
  }

  ngAfterViewInit(): void {
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
