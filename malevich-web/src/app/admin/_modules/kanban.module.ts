import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxKanbanComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxkanban';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxKanbanComponent],
    exports: [jqxKanbanComponent],
})
export class KanbanModule { }

