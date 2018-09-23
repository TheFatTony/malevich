import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-galleries-galleries-list',
  templateUrl: './galleries-list.component.html',
  styleUrls: ['./galleries-list.component.css']
})
export class GalleriesListComponent implements OnInit {

  showGrid: boolean = true;

  constructor() { }

  ngOnInit() {
  }

}
