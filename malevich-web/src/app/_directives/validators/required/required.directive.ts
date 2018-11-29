import {Attribute, ContentChild, Directive, forwardRef} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, NgModel, ValidationErrors, Validator} from "@angular/forms";

@Directive({
  selector: '[mchRequired][formControlName],[mchRequired][formControl],[mchRequired][ngModel]',
  providers: [
    {provide: NG_VALIDATORS, useExisting: forwardRef(() => RequiredDirective), multi: true}
  ]
})
export class RequiredDirective implements Validator {

  validate(control: AbstractControl): ValidationErrors | null {
    let value = control.value;

    if (value == null || value.length === 0)
      return {mchRequired: `Value required`};

    return null;
  }

}
