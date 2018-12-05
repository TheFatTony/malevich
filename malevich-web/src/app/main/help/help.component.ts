import {AfterViewInit, Component, OnInit} from '@angular/core';
import {HelpService} from '../../_services/help.service';
import {HelpCategoryDto} from '../../_transfer/helpCategoryDto';
import {TranslateService} from '@ngx-translate/core';
import {HelpTopicDto} from '../../_transfer/helpTopicDto';
import {HelpFilterDto} from '../../_transfer/helpFilterDto';
import {ContactUsDto} from '../../_transfer/contactUsDto';
import {ContactUsService} from '../../_services/contactus.service';

@Component({
  selector: 'app-main-help',
  templateUrl: './help.component.html',
  styleUrls: ['./help.component.css'],
  host: {
    '(window:resize)': 'onResize($event)'
  }
})
export class HelpComponent implements OnInit, AfterViewInit {

  helpCategories: HelpCategoryDto[];
  helpTopics: HelpTopicDto[];
  helpFilter: HelpFilterDto;
  contactUs: ContactUsDto;
  searchValue: string;

  constructor(private helpService: HelpService, private translate: TranslateService,
              private contactUsService: ContactUsService) {
  }

  ngOnInit() {
    this.contactUs = new ContactUsDto();
    this.helpFilter = new HelpFilterDto();
    this.getCategories();
    this.getTopic(1);
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSTabs.init('[role="tablist"]');
  }

  onResize(event) {
    setTimeout(function () {
      $['HSCore'].components.HSTabs.init('[role="tablist"]');
    }, 200);
  }

  getCategories(): void {
    this.helpService
      .getCategories()
      .subscribe(
        data => (this.helpCategories = data)
      );
  }

  getTopic(id: number): void {
    this.helpService.getTopic(id).subscribe(data => (this.helpTopics = data));
  }

  filterSearch(): void {
    this.helpFilter.lang = this.translate.currentLang;
    this.helpFilter.searchValue = this.searchValue;
    this.helpService.filterSearch(this.helpFilter).subscribe(data => (this.helpTopics = data));
  }

  submit(): void {
    this.contactUsService.save(this.contactUs).subscribe(
      () => {
        this.contactUs.name = '';
        this.contactUs.emailId = '';
        this.contactUs.phone = '';
        this.contactUs.message = '';
        this.contactUs.subject = '';
      }
    );
  }

}
