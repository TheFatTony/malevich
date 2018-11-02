import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxValidatorComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxvalidator';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxValidatorComponent],
  exports: [jqxValidatorComponent],
})
export class ValidatorModule {
}

