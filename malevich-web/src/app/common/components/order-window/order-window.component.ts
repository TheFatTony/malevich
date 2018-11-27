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

@Component({
  selector: 'mch-order-window',
  templateUrl: './order-window.component.html',
  styleUrls: ['./order-window.component.css']
})
export class OrderWindowComponent implements OnInit, AfterViewInit {


  @Input('artworkStock') attrArtWorkStock: ArtworkStockDto;
  @Input('orderType') attrOrderType: string;
  @Output() onOrderPlaced = new EventEmitter();

  @ViewChild('myWindow') myWindow: jqxWindowComponent;

  private x: number;
  private y: number;

  tradeTypes: TradeTypeDto [];
  tradeTypeDisplayFunc = (type: TradeTypeDto) => type.nameMl[this.translate.currentLang];
  expirationDateHidden: boolean = true;
  public newOrder: OrderDto = new OrderDto();

  constructor(private orderService: OrderService,
              public translate: TranslateService,
              private tradeTypeService: TradeTypeService) {
  }

  ngOnInit() {
    this.getTradeTypes();
  }

  ngAfterViewInit(): void {
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

  showExpirationDateInput(show: boolean) {
    this.expirationDateHidden = !show;

    if (show)
      this.myWindow.height(300);
    else
      this.myWindow.height(240);
  }

  onTradeTypeSelected(event: any) {
    if(!event.args.item)
      return;

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
    this.newOrder = new OrderDto();
    this.newOrder.artworkStock = this.artworkStock();
    this.newOrder.expirationDate = null;
    this.newOrder.tradeType = this.tradeTypes[0];
    this.newOrder.amount = 0;
    this.showExpirationDateInput(false);

    this.myWindow.width(310);
    this.myWindow.height(240);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  close() {
    this.myWindow.close();
  }

  sendButton(form: any) {
    if(form.invalid)
      return;

    if (this.orderType().toLocaleLowerCase() == 'ask')
      this.orderService.placeAsk(this.newOrder).subscribe(() => {
        this.onOrderPlaced.emit(this.newOrder);
      });

    if (this.orderType().toLocaleLowerCase() == 'bid')
      this.orderService.placeBid(this.newOrder).subscribe(() => {
        this.onOrderPlaced.emit(this.newOrder);
      });

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
