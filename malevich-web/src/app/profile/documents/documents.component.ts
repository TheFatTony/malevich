import {Component, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {environment} from '../../../environments/environment.dev';
import {DocumentsService} from '../../_services/documents.service';
import {Router} from '@angular/router';
import {DocumentsDto} from '../../_transfer/documentsDto';
import {DelayedChangeService} from "../../_services/delayed-change.service";
import {ParticipantService} from "../../_services/participant.service";

@Component({
  selector: 'app-profile-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;

  selectedRowIndex: number = -1;
  documents: DocumentsDto[];

  public url = environment.baseUrl;

  columns(names: any): any[] {
    return [
      {dataField: 'FILE_NAME', text: names['PROFILE.GRID.FILE_NAME'], width: '35%', columntype: 'textbox'},
      {dataField: 'DATE', text: names['PROFILE.GRID.DATE'], width: '35%', columntype: 'textbox'},
      {dataField: 'DOCUMENT_TYPE', text: names['PROFILE.GRID.DOCUMENT_TYPE'], width: '30%', columntype: 'textbox'}
    ];
  }

  constructor(public translate: TranslateService,
              private documentsService: DocumentsService,
              private delayedChangeService: DelayedChangeService,
              private participantService: ParticipantService,
              private router: Router) {
    this.updateGrid();
  }

  ngOnInit() {
    this.getCurrent();
    this.getDocs();
  }

  updateGrid() {
    this.translate
      .get([
        'PROFILE.GRID.FILE_NAME',
        'PROFILE.GRID.DATE',
        'PROFILE.GRID.DOCUMENT_TYPE'
      ])
      .subscribe(data => {
        this.myGrid.hideloadelement();
        this.myGrid.beginupdate();
        this.myGrid.setOptions
        ({
          columns: this.columns(data)
        });
        this.myGrid.endupdate();
      });
  }

  ngAfterViewInit(): void {
  }

  getDocs(): void {
    this.documentsService.getDocs().subscribe(data => {
      this.documents = data;
    });
  }

  getCurrent(): void {
    this.participantService
      .getCurrent()
      .subscribe(
        data => {
          if (data) {
            this.getDelayedChanges(data.id);
          }
        }
      );
  }

  getDelayedChanges(participantId: number): void {
    this.delayedChangeService
      .alertIfDelayedChanges("DOCUMENT", participantId)
      .subscribe();
  }

  onAddButtonClick() {
    this.router.navigate(['/profile/documents/add']);
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
