import {NgModule} from '@angular/core';
import {ButtonComponent} from './core/components/button.component';
import {TextInputComponent} from './core/components/text-input.component';
import {FormsModule} from '@angular/forms';
import {DropDownListComponent} from './core/components/dropdownlist.component';
import {DropDownListModule} from './admin/_modules/dropdownlist.module';


@NgModule({
  declarations: [ButtonComponent, TextInputComponent, DropDownListComponent],
  imports: [FormsModule, DropDownListModule],
  exports: [ButtonComponent, TextInputComponent, FormsModule, DropDownListComponent, DropDownListModule]
})
export class SharedModule {
}
