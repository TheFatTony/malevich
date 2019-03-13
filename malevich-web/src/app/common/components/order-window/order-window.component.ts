import {
  AfterViewInit,
  Component,
  EventEmitter,
  HostListener,
  Input,
  OnDestroy,
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

@Component({
  selector: 'mch-order-window',
  templateUrl: './order-window.component.html',
  styleUrls: ['./order-window.component.css']
})
export class OrderWindowComponent implements OnInit, AfterViewInit, OnDestroy {


  @Input('artworkStock') attrArtWorkStock: ArtworkStockDto;
  @Input('orderType') attrOrderType: string;
  @Output() onOrderPlaced = new EventEmitter();

  @ViewChild('myWindow') myWindow: jqxWindowComponent;

  private x: number;
  private y: number;

  expirationDateHidden: boolean = true;

  constructor(public translate: TranslateService) {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
  }

  ngOnDestroy(): void {
    this.myWindow.close();
  }

  showExpirationDateInput(show: boolean) {
    this.expirationDateHidden = !show;

    if (show)
      this.myWindow.height(300);
    else
      this.myWindow.height(240);
  }

  open() {
     this.showExpirationDateInput(false);

    this.myWindow.width(310);
    this.myWindow.height(240);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  close() {
    this.myWindow.close();
  }

  orderPlaced(order: OrderDto) {
    this.onOrderPlaced.emit(order);
    this.myWindow.close();
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
