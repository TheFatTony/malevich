import {Injectable} from '@angular/core';
import {AbstractControl, AbstractControlDirective, NgControl} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  constructor() {
  }

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
