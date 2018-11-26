import {Directive, forwardRef} from '@angular/core';
import {FormControl, NG_VALIDATORS, ValidationErrors, Validator} from "@angular/forms";

@Directive({
  selector: '[mchMatchTo][formControlName],[mchMatchTo][formControl],[mchMatchTo][ngModel]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => MatchToDirective), multi: true }
  ]
})
export class MatchToDirective implements Validator {

  constructor() { }

  registerOnValidatorChange(fn: () => void): void {
  }

  validate(control: FormControl): ValidationErrors | null {
    return undefined;
  }

}

export function matchOtherValidator (otherControlName: string) {

  let thisControl: FormControl;
  let otherControl: FormControl;

  return function matchOtherValidate(control: FormControl) {

    if (!control.parent) {
      return null;
    }

    // Initializing the validator.
    if (!thisControl) {
      thisControl = control;
      otherControl = control.parent.get(otherControlName) as FormControl;
      if (!otherControl) {
        throw new Error('matchOtherValidator(): other control is not found in parent group');
      }
      otherControl.valueChanges.subscribe(() => {
        thisControl.updateValueAndValidity();
      });
    }

    if (!otherControl) {
      return null;
    }

    if (otherControl.value !== thisControl.value) {
      return {
        matchOther: true
      };
    }

    return null;

  }
}


