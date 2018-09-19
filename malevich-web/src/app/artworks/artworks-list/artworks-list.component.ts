import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-artworks-artworks-list',
  templateUrl: './artworks-list.component.html',
  styleUrls: ['./artworks-list.component.css']
})
export class ArtworksListComponent implements OnInit {

  showGrid: boolean = true;

  constructor() { }

  ngOnInit() {
  }

}
