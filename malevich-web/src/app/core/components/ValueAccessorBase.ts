import {ControlValueAccessor} from '@angular/forms';
import {ElementRef, OnInit} from "@angular/core";


export class ValueAccessorBase<T> implements ControlValueAccessor, OnInit {
  private innerValue: T;


  private changed = new Array<(value: T) => void>();
  private touched = new Array<() => void>();
  private elementRef: ElementRef;

  constructor(containerElement: ElementRef) {
    this.elementRef = containerElement;
  }

  ngOnInit(): void {
    let parentElement = this.elementRef.nativeElement;
    let childElement = this.elementRef.nativeElement.firstChild;
    this.moveClasses(parentElement, childElement);
    this.moveStyles(parentElement, childElement);
  }

  moveClasses(parentEl: HTMLElement, childEl: HTMLElement): void {
    let classes: any = parentEl.classList;
    if (classes.length > 0) {
      childEl.classList.add(...classes);
    }
    parentEl.className = '';
  }

  moveStyles(parentEl: HTMLElement, childEl: HTMLElement): void {
    let style = parentEl.style.cssText;
    childEl.style.cssText = style
    parentEl.style.cssText = '';
  }

  get value(): T {
    return this.innerValue;
  }


  set value(value: T) {
    if (this.innerValue !== value) {
      this.innerValue = value;
      this.changed.forEach(f => f(value));
    }
  }

  touch() {
    this.touched.forEach(f => f());
  }

  writeValue(value: T) {
    this.innerValue = value;
  }


  registerOnChange(fn: (value: T) => void) {
    this.changed.push(fn);
  }


  registerOnTouched(fn: () => void) {
    this.touched.push(fn);
  }

}
