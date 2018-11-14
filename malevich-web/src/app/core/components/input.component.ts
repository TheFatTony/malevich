import {Component, forwardRef, ViewChild} from '@angular/core';
import {ValueAccessorBase} from "./ValueAccessorBase";
import {NG_VALUE_ACCESSOR, NgModel} from "@angular/forms";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => InputComponent),
  multi: true
};

@Component({
  selector: 'mchInput',
  template: '<jqxInput class="g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v3 rounded g-py-15 g-px-15"\n' +
            '[(ngModel)]="value"\n' +
            '[width]="\'100%\'" [theme]="malevich"></jqxInput>',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class InputComponent extends ValueAccessorBase<any>{

  @ViewChild(NgModel) model: NgModel;

  constructor() {
    super();
  }



}
