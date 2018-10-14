import {Component, OnInit, TemplateRef} from '@angular/core';
import {LoadingService} from "../../_services/loading.service";

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements OnInit {


  constructor(public loadingService: LoadingService) { }

  ngOnInit() {
  }

}
