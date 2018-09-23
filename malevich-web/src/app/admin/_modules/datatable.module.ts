import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxDataTableComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxdatatable';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxDataTableComponent],
    exports: [jqxDataTableComponent],
})
export class DataTableModule { }

