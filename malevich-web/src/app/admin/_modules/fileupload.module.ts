import { NgModule }       from '@angular/core';
import { CommonModule }   from '@angular/common';

import { jqxFileUploadComponent } from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxfileupload';

@NgModule({
    imports: [CommonModule],
    declarations: [jqxFileUploadComponent],
    exports: [jqxFileUploadComponent],
})
export class FileUploadModule { }

