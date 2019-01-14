import {Component, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {jqxComboBoxComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox';
import {Router} from '@angular/router';
import {DocumentTypeDto} from '../../../_transfer/documentTypeDto';
import {DocumentsService} from '../../../_services/documents.service';
import {DocumentsDto} from '../../../_transfer/documentsDto';
import {environment} from '../../../../environments/environment.dev';
import {ParticipantService} from "../../../_services/participant.service";
import {ParticipantDto} from "../../../_transfer/participantDto";
import {map, mergeMap} from "rxjs/operators";

@Component({
  selector: 'app-profile-gallery-storage-add',
  templateUrl: './document-add.component.html',
  styleUrls: ['./document-add.component.css']
})
export class DocumentAddComponent implements OnInit {

  @ViewChild('documentTypeComboBox') documentTypeComboBox: jqxComboBoxComponent;

  public url = environment.baseUrl;

  document: DocumentsDto;
  participant: ParticipantDto;
  documentTypes: any[];
  userType: string = 'trader';

  documentTypeDisplayFunc = (documentType: DocumentTypeDto) => {
    return documentType.nameMl[this.translate.currentLang];
  };

  constructor(private router: Router,
              public translate: TranslateService,
              private participantService: ParticipantService,
              private documentService: DocumentsService) {
  }

  ngOnInit() {
    this.document = new DocumentsDto();
    this.getInitValues();
  }

  getInitValues() {
    this.participantService.getCurrent()
      .pipe(mergeMap(p => {
          this.participant = p;
          this.userType = this.participantService.isGallery(this.participant) ? "gallery" : "trader";

          return this.documentService.getDocumentTypes(this.userType)
            .pipe(map(data => {
              this.documentTypes = data;
            }));
        })
      )
      .subscribe();
  }

  submit() {
    this.documentService.save(this.document).subscribe(() =>{
      this.router.navigate(['/profile/documents']);
    });
  }

  cancel() {
    this.router.navigate(['/profile/documents']);
  }


  onUploadEnd(event: any): void {
    let args = event.args;
    this.document.files = JSON.parse(args.response.toString()
      .replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">', '')
      .replace('<pre>', '')
      .replace('</pre>', ''));
  }

}
