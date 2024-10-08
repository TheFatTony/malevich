import {Component, OnInit, ViewChild} from '@angular/core';
import {FileService} from '../../../_services';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {FileDto} from '../../../../../node_modules/yinyang-core';

@Component({
  selector: 'app-files-list',
  templateUrl: './files-list.component.html',
  styleUrls: ['./files-list.component.css']
})
export class FilesListComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;
  files: FileDto[];


  constructor(private fileService: FileService) {
    }

  getFiles(): void {
    this.fileService
      .getFiles()
      .subscribe(
        data => (this.files = data)
      );
  }

  ngOnInit() {
    this.getFiles();
  }

}
