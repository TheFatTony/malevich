import {Component, ContentChild, ElementRef, forwardRef, Input} from '@angular/core';
import {NG_VALUE_ACCESSOR, NgModel} from "@angular/forms";
import {jqxMaskedInputComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxmaskedinput";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => MaskedInputComponent),
  multi: true
};

@Component({
  selector: 'mchMaskedInput',
  template: '<input class="g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v3 rounded g-py-15 g-px-15" type="text">',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class MaskedInputComponent extends jqxMaskedInputComponent {

  @ContentChild(NgModel) myModel: NgModel;

  @Input() showErrors: boolean = true;

  constructor(containerElement: ElementRef) {
    super(containerElement);
    this.attrWidth = '100%';
    this.attrHeight = 48;
  }
}
