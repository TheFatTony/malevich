import {Component, ElementRef, forwardRef} from '@angular/core';
import {NG_VALUE_ACCESSOR} from "@angular/forms";
import {jqxInputComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxinput";
import {jqxButtonComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxbuttons";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ButtonComponent),
  multi: true
};

@Component({
  selector: 'mchButton',
  template: '<button><ng-content></ng-content></button>',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class ButtonComponent extends jqxButtonComponent {

  constructor(containerElement: ElementRef) {
    super(containerElement);
    this.attrTheme = 'malevich';
  }
}
