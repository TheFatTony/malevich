import {Component, OnInit} from '@angular/core';
import {PaymentMethodService} from "../../_services/payment-method.service";
import {PaymentMethodDto} from "../../_transfer/paymentMethodDto";

@Component({
  selector: 'app-profile-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  methods: PaymentMethodDto[];

  constructor(private paymentMethodService: PaymentMethodService) {
  }

  ngOnInit() {
    this.getMethods();
  }

  getMethods(){
    this.paymentMethodService.getPaymentMethods().subscribe(data => {
      this.methods = data;
    });
  }

  onUpdate(method: PaymentMethodDto){
    this.getMethods();
  }

}
