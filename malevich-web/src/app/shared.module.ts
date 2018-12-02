import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {DropDownListModule} from './admin/_modules/dropdownlist.module';
import {ButtonComponent} from "yinyang-core/lib/components/button.component";
import {TextInputComponent} from "yinyang-core/lib/components/text-input.component";
import {DropDownListComponent} from "yinyang-core/lib/components/dropdownlist.component";


@NgModule({
  declarations: [ButtonComponent, TextInputComponent, DropDownListComponent],
  imports: [FormsModule, DropDownListModule],
  exports: [ButtonComponent, TextInputComponent, FormsModule, DropDownListComponent, DropDownListModule]
})
export class SharedModule {
}
