import {
  AfterViewInit,
  Component,
  ContentChild,
  ElementRef,
  forwardRef,
  Input,
  SimpleChanges,
  ViewChild
} from '@angular/core';
import {NG_VALUE_ACCESSOR, NgModel} from "@angular/forms";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ComboBoxComponent),
  multi: true
};

@Component({
  selector: 'mchComboBox',
  template: '<div class="g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v3 rounded"><ng-content></ng-content></div>',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class ComboBoxComponent extends jqxComboBoxComponent {

  @ContentChild(NgModel) ngModel: NgModel;

  @Input('auto-select') attrAutoSelect: boolean = true;

  constructor(containerElement: ElementRef) {
    super(containerElement);
    this.attrTheme = 'malevich';
    this.attrWidth = '100%';
    this.attrHeight = 48;
  }

  ngAfterViewInit() {
    super.ngAfterViewInit();

    this.syncSelection();
    this.ngModel.valueChanges.subscribe(() => this.syncSelection());
  }

  private syncSelection() {
    if (!this.attrAutoSelect || !this.attrSource || !this.ngModel.model)
      return;

    if ( this.getSelectedItem() && this.ngModel.model.id == this.getSelectedItem().value.id)
      return;

    let selectIndex = this.attrSource.findIndex(i => i.value.id == this.ngModel.model.id);
    this.selectIndex(selectIndex);
  }


}
