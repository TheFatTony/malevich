import {Component, OnInit} from '@angular/core';
import {PaymentMethodDto} from "../../../_transfer/paymentMethodDto";

@Component({
  selector: 'app-profile-payment-account',
  templateUrl: './payment-account.component.html',
  styleUrls: ['./payment-account.component.css']
})
export class PaymentAccountComponent implements OnInit {

  isEditing: boolean = false;

  editMethod: PaymentMethodDto;

  constructor() {

  }

  ngOnInit(): void {
  }

  onEdit(account: PaymentMethodDto) {
    this.editMethod = account;
    this.isEditing = true;
  }

  onSubmit(){
    this.isEditing = false;
  }

  onCancel(){
    this.isEditing = false;
  }

}
