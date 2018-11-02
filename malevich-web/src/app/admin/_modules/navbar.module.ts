import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxNavBarComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxnavbar';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxNavBarComponent],
  exports: [jqxNavBarComponent],
})
export class NavBarModule {
}
