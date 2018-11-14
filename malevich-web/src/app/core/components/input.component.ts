import {Component, ElementRef, forwardRef, Input, ViewChild} from '@angular/core';
import {ValueAccessorBase} from "./ValueAccessorBase";
import {NG_VALUE_ACCESSOR} from "@angular/forms";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => InputComponent),
  multi: true
};

@Component({
  selector: 'mchInput',
  template: '<jqxInput class="g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v3 rounded g-py-15 g-px-15"\n' +
    '[(ngModel)]="value"\n' +
    '[width]="attrWidth" ' +
    '[theme]="attrTheme" ' +
    '[placeHolder]="attrPlaceHolder"></jqxInput>',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class InputComponent extends ValueAccessorBase<any> {

  @Input('theme') attrTheme: string = 'malevich';
  @Input('width') attrWidth: string | number = '100%';
  @Input('placeHolder') attrPlaceHolder: string;


  constructor(containerElement: ElementRef) {
    super(containerElement);
  }
}
