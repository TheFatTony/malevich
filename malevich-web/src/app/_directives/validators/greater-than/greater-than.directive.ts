import {Attribute, Directive} from "@angular/core";
import {NG_VALIDATORS, Validator, ValidationErrors, AbstractControl} from '@angular/forms';

@Directive({
  selector: '[mchGreaterThan][formControlName],[mchGreaterThan][formControl],[mchGreaterThan][ngModel]',
  providers: [{provide: NG_VALIDATORS, useExisting: GreaterThanDirective, multi: true}]
})
export class GreaterThanDirective implements Validator {

  constructor(@Attribute('mchGreaterThan') private readonly greaterThan: number) {
    if (!greaterThan)
      this.greaterThan = 0;
  }

  validate(c: AbstractControl): ValidationErrors | null {
    let v = c.value;

    if (!v) return null;

    if (v > this.greaterThan)
      return null;

    return {"mchGreaterThan": `Value should be greater than ${this.greaterThan}`};
  }
}
