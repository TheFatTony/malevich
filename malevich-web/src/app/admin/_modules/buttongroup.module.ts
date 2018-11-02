import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxButtonGroupComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxbuttongroup';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxButtonGroupComponent],
  exports: [jqxButtonGroupComponent],
})
export class ButtonGroupModule {
}

