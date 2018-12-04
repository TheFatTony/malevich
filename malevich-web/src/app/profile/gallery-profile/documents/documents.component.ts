import {Component, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {environment} from '../../../../environments/environment.dev';
import {DocumentsService} from '../../../_services/documents.service';
import {Router} from '@angular/router';
import {DocumentsDto} from '../../../_transfer/documentsDto';

@Component({
  selector: 'app-profile-gallery-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  selectedRowIndex: number = -1;
  galleryDocuments: DocumentsDto[];

  public url = environment.baseUrl;

  columns: any[] =
    [
      {datafield: 'File Name', width: '35%', columntype: 'textbox'},
      {datafield: 'Date', width: '35%', columntype: 'textbox'},
      {datafield: 'Document Type', width: '30%', columntype: 'textbox'},
    ];

  constructor(public translate: TranslateService, private documentsService: DocumentsService, private router: Router) {
  }

  ngOnInit() {
    this.getGalleryDocs();
  }

  ngAfterViewInit(): void {
  }

  getGalleryDocs(): void {
    this.documentsService.getGalleryDocs().subscribe(data => {
      this.galleryDocuments = data;
    });
  }

  onAddButtonClick() {
    this.router.navigate(['/profile/gallery/documents/add']);
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  onDeleteButtonClick() {
    let deleted = this.galleryDocuments.splice(this.selectedRowIndex, 1)[0];
    this.documentsService.deleteDocument(deleted.id).subscribe();
    this.myGrid.refresh();
    this.selectedRowIndex = -1;
  }

}
