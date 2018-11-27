import {Component, forwardRef, Input} from '@angular/core';
import {AbstractControl, AbstractControlDirective, NG_VALUE_ACCESSOR} from "@angular/forms";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ControlErrorsComponent),
  multi: true
};

@Component({
  selector: 'mchErrors',
  template: '<div *ngIf="isInvalid(parentComponent)"\n' +
    '                 class="invalid-feedback d-block">\n' +
    '              <ul>\n' +
    '                <li *ngFor="let error of errors(parentComponent)">\n' +
    '                  {{error}}\n' +
    '                </li>\n' +
    '              </ul>\n' +
    '            </div>',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class ControlErrorsComponent {

  @Input('component') parentComponent:AbstractControl;

  isInvalid(obj: any): boolean {
    let control = this.getControl(obj);
    return control && control.errors && this.hasAnyProperty(control.errors) && (control.touched || control.dirty);
  }

  errors(obj: any): string[] {
    let control = this.getControl(obj);
    return Object.values(control.errors);
  }

  private getControl(obj: any): AbstractControl {
    if (obj instanceof AbstractControl)
      return obj;

    if (obj instanceof AbstractControlDirective)
      return obj.control;

    return null;
  }

  private hasAnyProperty(obj: any): boolean {
    for (let p in obj)
      return true;
    return false;
  }
}
