import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {TraderService} from "../../../../_services/trader.service";

@Component({
  selector: 'app-profile-trader-security-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit, AfterViewInit {

  @Input() trader;

  constructor(public translate: TranslateService, private traderService: TraderService) { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSMaskedInput.init('[data-mask]');
    $['HSCore'].components.HSModalWindow.init('[data-modal-target]');
    $['HSCore'].components.HSDatepicker.init('#datepickerDefault');
  }

  update() : void {
    console.info(this.trader.mobile);
    this.traderService.update(this.trader);
  }

}
