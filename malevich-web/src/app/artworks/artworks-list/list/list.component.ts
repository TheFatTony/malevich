import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-artworks-artworks-list-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  @Input() artworks;

  private url = environment.baseUrl;

  constructor(public translate: TranslateService) {
  }

  ngOnInit() {
  }

}
