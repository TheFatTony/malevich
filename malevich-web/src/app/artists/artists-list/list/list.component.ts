import {Component, Input, OnInit} from '@angular/core';
import {environment} from "../../../../environments/environment.dev";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-artists-list-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  @Input() artists;

  private url = environment.baseUrl;

  constructor(public translate: TranslateService) {
  }

  ngOnInit() {
  }

}
