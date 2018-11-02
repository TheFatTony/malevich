import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {jqxPasswordInputComponent} from 'node_modules/jqwidgets-scripts/jqwidgets-ts/angular_jqxpasswordinput';

@NgModule({
  imports: [CommonModule, FormsModule],
  declarations: [jqxPasswordInputComponent],
  exports: [jqxPasswordInputComponent],
})
export class PasswordInputModule {
}

