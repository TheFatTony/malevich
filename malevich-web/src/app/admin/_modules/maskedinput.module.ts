import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxMaskedInputComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxmaskedinput';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxMaskedInputComponent],
    exports: [jqxMaskedInputComponent],
})
export class MaskedInputModule { }

