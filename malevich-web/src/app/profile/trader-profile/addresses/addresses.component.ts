import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {AddressService} from "../../../_services/address.service";
import {TraderDto} from "../../../_transfer/traderDto";
import {AddressDto} from "../../../_transfer/addressDto";

@Component({
  selector: 'app-profile-trader-addresses',
  templateUrl: './addresses.component.html',
  styleUrls: ['./addresses.component.css']
})
export class AddressesComponent implements OnInit {

  @Input() trader: TraderDto;
  addresses: AddressDto[];

  constructor(public translate: TranslateService,
              private addressService: AddressService) { }

  ngOnInit() {
    this.getAddresses();
  }

  private getAddresses() {
    this.addressService
      .getByTrader(this.trader.id)
      .subscribe(
        data => (this.addresses = data)
      );
  }

}
