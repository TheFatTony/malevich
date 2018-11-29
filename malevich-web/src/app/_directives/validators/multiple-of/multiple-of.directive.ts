import {Attribute, Directive, forwardRef} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, ValidationErrors, Validator} from "@angular/forms";

@Directive({
  selector: '[mchMultipleOf][formControlName],[mchMultipleOf][formControl],[mchMultipleOf][ngModel]',
  providers: [
    {provide: NG_VALIDATORS, useExisting: forwardRef(() => MultipleOfDirective), multi: true}
  ]
})
export class MultipleOfDirective implements Validator {

  constructor(@Attribute('mchMultipleOf') private readonly multipleOf: number) {
    if(!multipleOf)
      this.multipleOf = 10;
  }

  validate(control: AbstractControl): ValidationErrors | null {
    let value = control.value;

    if(!value) return null;

    if (value % this.multipleOf != 0)
      return {mchMultipleOfTen: `Value should be a multiple of ${this.multipleOf}`}

    return null;
  }

}
