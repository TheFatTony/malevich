import {Component, ElementRef, forwardRef} from '@angular/core';
import {NG_VALUE_ACCESSOR} from "@angular/forms";
import {jqxDateTimeInputComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxdatetimeinput";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => DateTimeInputComponent),
  multi: true
};

@Component({
  selector: 'mchDateTimeInput',
  template: '<input class="g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v3 rounded" [(ngModel)]="ngValue">',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class DateTimeInputComponent extends jqxDateTimeInputComponent {

  constructor(containerElement: ElementRef) {
    super(containerElement);
    this.attrTheme = 'malevich';
    this.attrWidth = '100%';
    this.attrHeight = 48;
  }
}
