import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {TranslateService} from "@ngx-translate/core";
import {OrderDto} from "../../../_transfer/orderDto";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'mch-order-window',
  templateUrl: './order-window.component.html',
  styleUrls: ['./order-window.component.css']
})
export class OrderWindowComponent implements OnInit, AfterViewInit, OnDestroy {


  @Input('artworkStock') attrArtWorkStock: ArtworkStockDto;
  @Input('orderType') attrOrderType: string;
  @Output() onOrderPlaced = new EventEmitter();

  @ViewChild('myWindow') myWindow: TemplateRef<any>;

  expirationDateHidden: boolean = true;
  private modalRef: NgbModalRef;

  constructor(public translate: TranslateService,
              private modalService: NgbModal) {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
  }

  ngOnDestroy(): void {
    this.modalService.dismissAll();
  }

  showExpirationDateInput(show: boolean) {
    this.expirationDateHidden = !show;
  }

  open() {
    this.showExpirationDateInput(false);
    this.modalRef = this.modalService.open(this.myWindow, { centered: true });
  }

  close(reason?) {
    if (this.modalRef)
      this.modalRef.close(reason);
  }

  orderPlaced(order: OrderDto) {
    this.close();
    this.onOrderPlaced.emit(order);
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
}
