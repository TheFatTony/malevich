import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxExpanderComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxexpander';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxExpanderComponent],
    exports: [jqxExpanderComponent],
})
export class ExpanderModule { }

