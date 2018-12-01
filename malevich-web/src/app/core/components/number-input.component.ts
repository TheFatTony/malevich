import {Component, ContentChild, ElementRef, forwardRef, Input} from '@angular/core';
import {NG_VALUE_ACCESSOR, NgModel} from "@angular/forms";
import {jqxNumberInputComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxnumberinput";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => NumberInputComponent),
  multi: true
};

@Component({
  selector: 'mchNumberInput',
  template: '<input class="g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v3 rounded">',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class NumberInputComponent extends jqxNumberInputComponent {

  @ContentChild(NgModel) myModel: NgModel;

  @Input() showErrors: boolean = true;

  constructor(containerElement: ElementRef) {
    super(containerElement);
    this.attrWidth = '100%';
    this.attrHeight = 48;
  }
}
