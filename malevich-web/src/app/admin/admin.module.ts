import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MainMenuComponent} from './main-menu/main-menu.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {
  BarGaugeModule,
  BulletChartModule,
  ButtonGroupModule,
  ButtonModule,
  CalendarModule,
  ChartModule,
  CheckBoxModule,
  ColorPickerModule,
  ComboBoxModule,
  ComplexInputModule,
  DataTableModule,
  DateTimeInputModule,
  DockingLayoutModule,
  DockingModule,
  DockPanelModule,
  DragDropModule,
  DrawModule,
  DropDownButtonModule,
  DropDownListModule,
  EditorModule,
  ExpanderModule,
  FileUploadModule,
  FormattedInputModule,
  FormModule,
  GaugeModule,
  GridModule,
  InputModule,
  KanbanModule,
  KnobModule,
  LayoutModule,
  LinearGaugeModule,
  LinkButtonModule,
  ListBoxModule,
  ListMenuModule,
  LoaderModule,
  MaskedInputModule,
  MenuModule,
  NavBarModule,
  NavigationBarModule,
  NotificationModule,
  NumberInputModule,
  PanelModule,
  PasswordInputModule,
  PivotDesignerModule,
  PivotGridModule,
  PopoverModule,
  ProgressBarModule,
  RadioButtonModule,
  RangeSelectorModule,
  RatingModule,
  RepeatButtonModule,
  ResponsivePanelModule,
  RibbonModule,
  SchedulerModule,
  ScrollBarModule,
  ScrollViewModule,
  SliderModule,
  SortableModule,
  SplitterModule,
  SwitchButtonModule,
  TabsModule,
  TagCloudModule,
  TextAreaModule,
  ToggleButtonModule,
  ToolBarModule,
  TooltipModule,
  TreeGridModule,
  TreeMapModule,
  TreeModule,
  ValidatorModule,
  WindowModule
} from './_modules';
import {UserListComponent} from './user/user-list/user-list.component';
import {AdminComponent} from './admin.component';
import {AdminRoutingModule} from './admin-routing.module';
import {FilesListComponent} from './files/files-list/files-list.component';
import {UsersService} from './_services/users.service';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    BarGaugeModule,
    BulletChartModule,
    ButtonModule,
    ButtonGroupModule,
    CalendarModule,
    ChartModule,
    CheckBoxModule,
    ColorPickerModule,
    ComboBoxModule,
    ComplexInputModule,
    DataTableModule,
    DateTimeInputModule,
    DockingModule,
    DockingLayoutModule,
    DockPanelModule,
    DragDropModule,
    DrawModule,
    DropDownButtonModule,
    DropDownListModule,
    EditorModule,
    ExpanderModule,
    FileUploadModule,
    FormModule,
    FormattedInputModule,
    GaugeModule,
    GridModule,
    InputModule,
    KanbanModule,
    KnobModule,
    LayoutModule,
    LinearGaugeModule,
    LinkButtonModule,
    ListBoxModule,
    ListMenuModule,
    LoaderModule,
    MaskedInputModule,
    MenuModule,
    NavBarModule,
    NavigationBarModule,
    NotificationModule,
    NumberInputModule,
    PanelModule,
    PasswordInputModule,
    PivotDesignerModule,
    PivotGridModule,
    PopoverModule,
    ProgressBarModule,
    RadioButtonModule,
    RangeSelectorModule,
    RatingModule,
    RepeatButtonModule,
    ResponsivePanelModule,
    RibbonModule,
    SchedulerModule,
    ScrollBarModule,
    ScrollViewModule,
    SliderModule,
    SortableModule,
    SplitterModule,
    SwitchButtonModule,
    TabsModule,
    TagCloudModule,
    TextAreaModule,
    ToggleButtonModule,
    ToolBarModule,
    TooltipModule,
    TreeModule,
    TreeGridModule,
    TreeMapModule,
    ValidatorModule,
    WindowModule
  ],
  declarations: [
    AdminComponent,
    MainMenuComponent,
    DashboardComponent,
    UserListComponent,
    FilesListComponent],
  providers: [UsersService]
})
export class AdminModule {
}
