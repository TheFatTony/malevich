import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxRangeSelectorComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxrangeselector';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxRangeSelectorComponent],
  exports: [jqxRangeSelectorComponent],
})
export class RangeSelectorModule {
}

