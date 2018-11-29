import {Attribute, ContentChild, Directive, forwardRef} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, NgModel, ValidationErrors, Validator, Validators} from "@angular/forms";

@Directive({
  selector: '[mchMobile][formControlName],[mchMobile][formControl],[mchMobile][ngModel]',
  providers: [
    {provide: NG_VALIDATORS, useExisting: forwardRef(() => MobileDirective), multi: true}
  ]
})
export class MobileDirective implements Validator {

  private patternValidator = Validators.pattern(/\+\d{1,3} \(\d{3}\) \d{3}-\d{4}/);

  validate(control: AbstractControl): ValidationErrors | null {

    if (this.patternValidator(control) == null)
      return null;

    return {mchMobile: `Value is not a valid phone number`};
  }

}
