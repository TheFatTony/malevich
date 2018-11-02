import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxLinearGaugeComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxlineargauge';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxLinearGaugeComponent],
  exports: [jqxLinearGaugeComponent],
})
export class LinearGaugeModule {
}

