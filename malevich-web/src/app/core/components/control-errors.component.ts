import {Component, forwardRef, Input, OnInit} from '@angular/core';
import {
  AbstractControl,
  AbstractControlDirective,
  FormControl,
  FormGroup,
  NG_VALUE_ACCESSOR,
  NgForm
} from "@angular/forms";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ControlErrorsComponent),
  multi: true
};

@Component({
  selector: 'mchErrors',
  template: '<div *ngIf="isInvalid"\n' +
    '                 class="invalid-feedback d-block">\n' +
    '              <ul class="mb-0">\n' +
    '                <li *ngFor="let error of errors">\n' +
    '                  {{error}}\n' +
    '                </li>\n' +
    '              </ul>\n' +
    '            </div>',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class ControlErrorsComponent implements OnInit {

  @Input('component') thisComponent: AbstractControlDirective;

  ngOnInit(): void {
  }

  get isInvalid(): boolean {
    let control = this.getControl(this.thisComponent);
    return control && control.errors && this.hasAnyProperty(control.errors) && (control.touched || control.dirty);
  }

  get errors(): string[] {
    let control = this.getControl(this.thisComponent);
    if (!control || !control.errors)
      return [];
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
