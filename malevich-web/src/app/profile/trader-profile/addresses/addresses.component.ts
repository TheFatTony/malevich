import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {AddressService} from "../../../_services/address.service";
import {TraderDto} from "../../../_transfer/traderDto";
import {AddressDto} from "../../../_transfer/addressDto";
import {CountryDto} from "../../../_transfer/countryDto";

@Component({
  selector: 'app-profile-trader-addresses',
  templateUrl: './addresses.component.html',
  styleUrls: ['./addresses.component.css']
})
export class AddressesComponent implements OnInit, AfterViewInit {

  @Input() trader: TraderDto;
  @Input() countries: CountryDto[];
  addresses: AddressDto[];
  newAddress: AddressDto;

  constructor(public translate: TranslateService,
              private addressService: AddressService) {
    this.initNewAddress();
  }

  ngOnInit() {

  }

  ngAfterViewInit(): void {
    this.getAddresses();
  }

  private initNewAddress() {
    this.newAddress = new AddressDto();
    this.newAddress.country = new CountryDto();
  }

  private getAddresses() {
    this.addressService
      .getByTrader(this.trader.id)
      .subscribe(
        data => (this.addresses = data)
      );
  }

  addAddress() {
    this.addressService.create(this.newAddress);
    this.initNewAddress();
    this.addresses.push(this.newAddress);
  }

}
