import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxDragDropComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxdragdrop';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxDragDropComponent],
    exports: [jqxDragDropComponent],
})
export class DragDropModule { }

