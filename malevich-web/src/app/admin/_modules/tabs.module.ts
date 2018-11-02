import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxTabsComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxtabs';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxTabsComponent],
  exports: [jqxTabsComponent],
})
export class TabsModule {
}

