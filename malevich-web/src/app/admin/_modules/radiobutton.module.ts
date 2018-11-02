import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxRadioButtonComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxradiobutton';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxRadioButtonComponent],
  exports: [jqxRadioButtonComponent],
})
export class RadioButtonModule {
}

