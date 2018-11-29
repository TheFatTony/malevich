import {Component, ElementRef, forwardRef, Input} from '@angular/core';
import {FormControl, FormGroup, NG_VALUE_ACCESSOR, NgForm} from "@angular/forms";
import {jqxButtonComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxbuttons";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ButtonComponent),
  multi: true
};

@Component({
  selector: 'mchButton',
  template: '<button type="button"><ng-content></ng-content></button>',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class ButtonComponent extends jqxButtonComponent {

  @Input() submitForm: NgForm;

  constructor(containerElement: ElementRef) {
    super(containerElement);
    this.attrHeight = 35;
  }

  private touchAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched();
      } else if (control instanceof FormGroup) {
        this.touchAllFormFields(control);
      }
    });
  }

  __wireEvents__(): void {
    this.host.on('click', (eventData: any) => {
      if (this.submitForm) {
        if (this.submitForm.invalid) {
          this.touchAllFormFields(this.submitForm.control);
        }
        else
          this.submitForm.ngSubmit.emit();
      }

      this.onClick.emit(eventData);
    });
  }
}
