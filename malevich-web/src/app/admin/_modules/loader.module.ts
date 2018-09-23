import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxLoaderComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxloader';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxLoaderComponent],
    exports: [jqxLoaderComponent],
})
export class LoaderModule { }

