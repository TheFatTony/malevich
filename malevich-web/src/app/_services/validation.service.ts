import { Injectable } from '@angular/core';
import {AbstractControl} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  constructor() { }

  isInValid(control:AbstractControl) : boolean{
    return control && control.errors && this.hasAnyProperty(control.errors) && (control.touched || control.dirty);
  }

  errors(obj:AbstractControl) : string[]{
    return Object.values(obj.errors);
  }

  private hasAnyProperty(obj:any):boolean{
    for( let p in obj)
      return true;
    return false;
  }
}
