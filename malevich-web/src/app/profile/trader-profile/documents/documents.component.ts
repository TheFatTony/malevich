import {Component, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {environment} from '../../../../environments/environment.dev';
import {DocumentsService} from '../../../_services/documents.service';
import {DocumentsDto} from '../../../_transfer/documentsDto';
import {Router} from '@angular/router';

@Component({
  selector: 'app-profile-trader-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  selectedRowIndex: number = -1;
  documents: DocumentsDto[];

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
    this.getTraderDocs();
  }

  ngAfterViewInit(): void {
  }

  getTraderDocs(): void {
    this.documentsService.getTraderDocs().subscribe(data => {
      this.documents = data;
    });
  }

  onAddButtonClick() {
    this.router.navigate(['/profile/trader/documents/add']);
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  onDeleteButtonClick() {
    let deleted = this.documents.splice(this.selectedRowIndex, 1)[0];
    this.documentsService.deleteDocument(deleted.id).subscribe();
    this.myGrid.refresh();
    this.selectedRowIndex = -1;
  }

}
