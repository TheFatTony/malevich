import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TradeTypeDto} from "../../../_transfer/tradeTypeDto";
import {OrderDto} from "../../../_transfer/orderDto";
import {OrderService} from "../../../_services/order.service";
import {TranslateService} from "@ngx-translate/core";
import {TradeTypeService} from "../../../_services/trade-type.service";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";

@Component({
  selector: 'mch-order-edit',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {

  @Input() artworkStock: ArtworkStockDto;
  @Input() amount: number = 0;
  @Input() orderType: string = 'bid';

  @Output() onOrderPlaced = new EventEmitter();
  @Output() onCancel = new EventEmitter();

  tradeTypes: TradeTypeDto [];
  tradeTypeDisplayFunc = (type: TradeTypeDto) => type.nameMl[this.translate.currentLang];
  expirationDateHidden: boolean = true;

  constructor(private orderService: OrderService,
              public translate: TranslateService,
              private tradeTypeService: TradeTypeService) {
  }

  ngOnInit() {
    // this.getTradeTypes();
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

    // if (show)
    //   this.myWindow.height(300);
    // else
    //   this.myWindow.height(240);
  }

  onTradeTypeSelected(event: any) {
    if (!event.args.item)
      return;

    // switch (event.args.item.value.id) {
    //   case "GTC_": {
    //     this.newOrder.expirationDate = null;
    //     this.showExpirationDateInput(false);
    //
    //     break;
    //   }
    //   case "GTT0": {
    //     this.newOrder.expirationDate = this.newOrder.expirationDate || new Date();
    //     this.showExpirationDateInput(false);
    //     break;
    //   }
    //   case "GTD_": {
    //     this.newOrder.expirationDate = this.newOrder.expirationDate || new Date();
    //     this.showExpirationDateInput(true);
    //     break;
    //   }
    // }
  }

  onFormSubmit() {

    const newOrder = new OrderDto();
    newOrder.artworkStock = this.artworkStock;
    newOrder.expirationDate = null;
    newOrder.tradeType = null;
    newOrder.amount = this.amount;

    this.orderType = (this.orderType || '').toLocaleLowerCase();

    if (this.orderType == 'ask')
      this.orderService.placeAsk(newOrder).subscribe(() => {
        this.onOrderPlaced.emit(newOrder);
      });

    if (this.orderType == 'bid')
      this.orderService.placeBid(newOrder).subscribe(() => {
        this.onOrderPlaced.emit(newOrder);
      });
  }

  cancel() {
    this.onCancel.next();
  }
}
