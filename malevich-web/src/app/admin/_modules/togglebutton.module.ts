import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxToggleButtonComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxtogglebutton';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxToggleButtonComponent],
    exports: [jqxToggleButtonComponent],
})
export class ToggleButtonModule { }

