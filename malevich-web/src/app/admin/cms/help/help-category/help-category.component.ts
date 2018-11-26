import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {TranslateService} from '@ngx-translate/core';
import {HelpCategoryDto} from '../../../../_transfer/helpCategoryDto';
import {HelpService} from '../../../../_services/help.service';
import {jqxWindowComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow';
import {jqxValidatorComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxvalidator';

@Component({
  selector: 'app-help-category-list',
  templateUrl: './help-category.component.html',
  styleUrls: ['./help-category.component.css']
})
export class HelpCategoryComponent implements OnInit {
  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('myValidator') myValidator: jqxValidatorComponent;
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  helpCategory: HelpCategoryDto[];
  newHelpCategory: HelpCategoryDto;

  x: number;
  y: number;

  constructor(private helpService: HelpService, public translate: TranslateService) {
  }

  ngOnInit() {
    this.getCategories();
  }

  getCategories(): void {
    this.helpService
      .getCategories()
      .subscribe(
        data => (this.helpCategory = data)
      );
  }

  openWindow() {
    this.newHelpCategory = new HelpCategoryDto();
    this.myWindow.width(310);
    this.myWindow.height(240);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  sendButton() {
    let addHelpCategory = new HelpCategoryDto();
    addHelpCategory.categoryNameMl = this.newHelpCategory.categoryNameMl;
    this.helpService.addCategory(addHelpCategory).subscribe(
      () => {
        this.getCategories();
      }
    );
  }

  onNameChange($event, lang: string) {
    if (!$event)
      return;

    if (!this.newHelpCategory)
      this.newHelpCategory = new HelpCategoryDto();

    if (!this.newHelpCategory.categoryNameMl)
      this.newHelpCategory.categoryNameMl = new Map<string, string>();

    this.newHelpCategory.categoryNameMl[lang] = $event;
  }

  onMyWindowOpen() {
  }

}
