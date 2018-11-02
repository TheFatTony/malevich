import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxRibbonComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxribbon';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxRibbonComponent],
  exports: [jqxRibbonComponent],
})
export class RibbonModule {
}

