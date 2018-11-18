import {
  Component,
  ContentChild,
  ElementRef,
  forwardRef,
  Input, SimpleChanges
} from '@angular/core';
import {NG_VALUE_ACCESSOR, NgModel} from "@angular/forms";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {jqxDropDownListComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxdropdownlist";

// default equality comparison implementation
const deepEquals = (a: any, b: any) => {
  // dummy implementation
  return JSON.stringify(a) === JSON.stringify(b);
};

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => DropDownListComponent),
  multi: true
};

@Component({
  selector: 'mchDropDownList',
  template: '<div class="g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v3 rounded"><ng-content></ng-content></div>',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class DropDownListComponent extends jqxDropDownListComponent {

  @ContentChild(NgModel) ngModel: NgModel;

  @Input('auto-select') attrAutoSelect: boolean = true;
  @Input('objectSource') attrObjectSource: any[];
  @Input('valueEqualFunc') attrValueEqualFunc: (a: any, b: any) => boolean = deepEquals;
  @Input('displayFunc') attrDisplayFunc: (item: any) => string = (item: any) => item.toString();
  @Input('valueFunc') attrValueFunc: (item: any) => any = (item: any) => item;

  constructor(containerElement: ElementRef) {
    super(containerElement);
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

    if (this.getSelectedItem() && this.attrValueEqualFunc(this.getSelectedItem().value, this.ngModel.model))
      return;

    let selectIndex = this.attrSource.findIndex(i => this.attrValueEqualFunc(i.value, this.ngModel.model));
    this.selectIndex(selectIndex);
  }


  ngOnChanges(changes: SimpleChanges): boolean {

    if (changes['attrObjectSource']) {
      let currentValue: any[] = changes['attrObjectSource'].currentValue;

      if (currentValue) {
        this.attrSource = currentValue.map(i => ({
            title: this.attrDisplayFunc(i),
            value: this.attrValueFunc(i)
          })
        );
        this.displayMember('title');
        this.valueMember('value');
      }
    }

    return super.ngOnChanges(changes);
  }
}
