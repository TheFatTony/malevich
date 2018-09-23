import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxPopoverComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxpopover';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxPopoverComponent],
    exports: [jqxPopoverComponent],
})
export class PopoverModule { }

