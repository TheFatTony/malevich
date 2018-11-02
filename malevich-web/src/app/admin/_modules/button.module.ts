import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxButtonComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxbuttons';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxButtonComponent],
  exports: [jqxButtonComponent],
})
export class ButtonModule {
}

