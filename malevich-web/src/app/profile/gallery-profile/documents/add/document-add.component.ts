import {Component, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {jqxComboBoxComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox';
import {Router} from '@angular/router';
import {DocumentTypeDto} from '../../../../_transfer/documentTypeDto';
import {DocumentsService} from '../../../../_services/documents.service';
import {DocumentsDto} from '../../../../_transfer/documentsDto';
import {environment} from '../../../../../environments/environment.dev';

@Component({
  selector: 'app-profile-gallery-storage-add',
  templateUrl: './document-add.component.html',
  styleUrls: ['./document-add.component.css']
})
export class DocumentAddComponent implements OnInit {

  @ViewChild('documentTypeComboBox') categoryComboBox: jqxComboBoxComponent;

  public url = environment.baseUrl;

  document: DocumentsDto;
  documentTypes: any[];
  userType: string = 'gallery';

  documentTypeDisplayFunc = (documentType: DocumentTypeDto) => {
    return documentType.typeName;
  };

  constructor(private router: Router, public translate: TranslateService, private documentService: DocumentsService) {
  }

  ngOnInit() {
    this.getDocumentTypes(this.userType);
  }

  getDocumentTypes(userType: string): void {
    this.documentService.getDocumentTypes(userType).subscribe(data => (
      this.documentTypes = data
    ));
  }

  submit() {
    this.documentService.save(this.document).subscribe();
  }

  cancel() {
    this.router.navigate(['/profile/gallery/documents/list/gallery']);
  }

  onTypeComboBoxChange($event) {
    if (!$event)
      return;

    if (!this.document)
      this.document = new DocumentsDto();

    this.document.documentType = $event;
  }

  onUploadEnd(event: any): void {
    let args = event.args;
    this.document.thumbnail = JSON.parse(args.response.toString()
      .replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">', '')
      .replace('<pre>', '')
      .replace('</pre>', ''));
  }

}
