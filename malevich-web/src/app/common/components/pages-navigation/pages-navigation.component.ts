import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PageParamsDto} from "../../../_transfer/pageParamsDto";
import {PageService} from "../../../_services/page.service";

@Component({
  selector: 'app-pages-navigation',
  templateUrl: './pages-navigation.component.html',
  styleUrls: ['./pages-navigation.component.css']
})
export class PagesNavigationComponent implements OnInit {

  @Output() onPageChanged = new EventEmitter<number>();

  @Input()
  set pageParams(value: PageParamsDto) {
    if(!value || !value.totalElements) return;
    this.pager = this.pageService.getPager(value.totalElements, value.number, value.size);
  }

  pager: any = {};

  constructor(private pageService: PageService) {
  }

  ngOnInit() {
  }

  setPage(page: number) {
    this.onPageChanged.next(page);
  }


}
