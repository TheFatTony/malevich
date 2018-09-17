import {Component, OnInit, ViewChild} from '@angular/core';
import {FileService} from '../../../_services';
import { File } from '../../../_transfer';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';

@Component({
  selector: 'app-files-list',
  templateUrl: './files-list.component.html',
  styleUrls: ['./files-list.component.css']
})
export class FilesListComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;
  files: File[];
  constructor( private fileService: FileService) { }


  getFiles(): void {
    this.fileService
      .getFiles()
      .subscribe(
        heroes => (this.files = heroes)
      );
  }

  ngOnInit() {
    this.getFiles();
  }

}
