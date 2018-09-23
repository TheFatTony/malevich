import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxPivotDesignerComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxpivotdesigner';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxPivotDesignerComponent],
    exports: [jqxPivotDesignerComponent],
})
export class PivotDesignerModule { }

