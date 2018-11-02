import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxListMenuComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxlistmenu';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxListMenuComponent],
  exports: [jqxListMenuComponent],
})
export class ListMenuModule {
}

