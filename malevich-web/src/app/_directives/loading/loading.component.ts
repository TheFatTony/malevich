import {Component, OnInit} from '@angular/core';
import {LoadingService} from "../../_services/loading.service";

const PrimaryWhite = '#ffffff';
const SecondaryGrey = '#ccc';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements OnInit {

  public primaryColour = PrimaryWhite;
  public secondaryColour = SecondaryGrey;


  constructor(public loadingService: LoadingService) {
  }

  ngOnInit() {
  }

}
