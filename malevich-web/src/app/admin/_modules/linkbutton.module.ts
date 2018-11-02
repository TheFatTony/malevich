import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {jqxLinkButtonComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxlinkbutton';

@NgModule({
  imports: [CommonModule],
  declarations: [jqxLinkButtonComponent],
  exports: [jqxLinkButtonComponent],
})
export class LinkButtonModule {
}

