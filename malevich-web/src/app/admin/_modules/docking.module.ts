import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxDockingComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxdocking';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxDockingComponent],
    exports: [jqxDockingComponent],
})
export class DockingModule { }

