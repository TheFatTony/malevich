import {Component, Input, OnInit} from '@angular/core';
import {environment} from "../../../../environments/environment.dev";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-galleries-list-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit {

  @Input() galleries;

  private url = environment.baseUrl;

  constructor(public translate: TranslateService) {
  }

  ngOnInit() {
  }

}
