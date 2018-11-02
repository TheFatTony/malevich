import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxBulletChartComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxbulletchart';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxBulletChartComponent],
  exports: [jqxBulletChartComponent],
})
export class BulletChartModule {
}

