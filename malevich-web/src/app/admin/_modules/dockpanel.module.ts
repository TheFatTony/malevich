import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxDockPanelComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxdockpanel';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxDockPanelComponent],
    exports: [jqxDockPanelComponent],
})
export class DockPanelModule { }

