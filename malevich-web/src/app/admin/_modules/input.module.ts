import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {jqxInputComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxinput';

@NgModule({
  imports: [CommonModule, FormsModule],
  declarations: [jqxInputComponent],
  exports: [jqxInputComponent],
})
export class InputModule {
}

