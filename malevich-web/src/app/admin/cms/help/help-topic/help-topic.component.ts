import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {TranslateService} from '@ngx-translate/core';
import {HelpService} from '../../../../_services/help.service';
import {jqxWindowComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow';
import {jqxValidatorComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxvalidator';
import {HelpTopicDto} from '../../../../_transfer/helpTopicDto';
import {HelpCategoryDto} from '../../../../_transfer/helpCategoryDto';

@Component({
  selector: 'app-help-topic-list',
  templateUrl: './help-topic.component.html',
  styleUrls: ['./help-topic.component.css']
})
export class HelpTopicComponent implements OnInit, AfterViewInit {
  @ViewChild('myWindow') myWindow: jqxWindowComponent;
  @ViewChild('myValidator') myValidator: jqxValidatorComponent;
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  helpTopics: HelpTopicDto[];
  helpTopic: HelpTopicDto;
  helpCategories: any[];

  x: number;
  y: number;

  constructor(private helpService: HelpService, public translate: TranslateService) {
  }

  ngOnInit() {
    this.helpTopic = new HelpTopicDto();
    this.getTopics();
    this.getCategories();
  }

  ngAfterViewInit(): void {
  }

  helpCategoryDisplayFunc = (helpCategory: HelpCategoryDto) => {
    return helpCategory.categoryNameMl[this.translate.currentLang];
  };

  getCategories(): void {
    this.helpService
      .getCategories()
      .subscribe(
        data => (this.helpCategories = data)
      );
  }

  getTopics(): void {
    this.helpService
      .getTopics()
      .subscribe(
        data => (this.helpTopics = data)
      );
  }

  openWindow() {
    this.myWindow.width(800);
    this.myWindow.height(700);
    this.myWindow.open();
    this.myWindow.move(this.x, this.y);
  }

  sendButton() {
    console.log(this.helpTopic);
    this.helpService.addTopic(this.helpTopic).subscribe(
      () => {
        this.getTopics();
      }
    );
  }

  onTitleChange($event, lang: string) {
    if (!$event)
      return;

    if (!this.helpTopic)
      this.helpTopic = new HelpTopicDto();

    if (!this.helpTopic.topicNameMl)
      this.helpTopic.topicNameMl = new Map<string, string>();

    this.helpTopic.topicNameMl[lang] = $event;
  }

  onBodyChange($event, lang: string) {
    if (!$event)
      return;

    if (!this.helpTopic)
      this.helpTopic = new HelpTopicDto();

    if (!this.helpTopic.bodyMl)
      this.helpTopic.bodyMl = new Map<string, string>();

    this.helpTopic.bodyMl[lang] = $event;
  }

  onMyWindowOpen() {
  }

}
