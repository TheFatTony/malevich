import {Attribute, ContentChild, Directive, forwardRef} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, NgModel, ValidationErrors, Validator, Validators} from "@angular/forms";

@Directive({
  selector: '[mchEmail][formControlName],[mchEmail][formControl],[mchEmail][ngModel]',
  providers: [
    {provide: NG_VALIDATORS, useExisting: forwardRef(() => EmailDirective), multi: true}
  ]
})
export class EmailDirective implements Validator {

  private patternValidator = Validators.email;

  validate(control: AbstractControl): ValidationErrors | null {

    if (this.patternValidator(control) == null)
      return null;

    return {mchEmail: `Value is not a valid email address`};
  }

}
