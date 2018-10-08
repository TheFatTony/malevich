import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-artworks-list-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit {

  @Input() artworks;

  private url = environment.baseUrl;

  constructor(public translate: TranslateService) {
  }

  ngOnInit() {
  }

}
