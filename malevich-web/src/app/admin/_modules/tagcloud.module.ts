import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxTagCloudComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxtagcloud';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxTagCloudComponent],
  exports: [jqxTagCloudComponent],
})
export class TagCloudModule {
}

