import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
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

  @ViewChild('documentTypeComboBox') documentTypeComboBox: jqxComboBoxComponent;

  public url = environment.baseUrl;

  galleryDocument: DocumentsDto;
  documentTypes: any[];
  userType: string = 'gallery';

  constructor(private router: Router, public translate: TranslateService, private documentService: DocumentsService) {
  }

  ngOnInit() {
    this.galleryDocument = new DocumentsDto();
    this.getDocumentTypes(this.userType);
  }

  documentTypeDisplayFunc = (documentType: DocumentTypeDto) => {
    return documentType.nameMl[this.translate.currentLang];
  };

  getDocumentTypes(userType: string): void {
    this.documentService.getDocumentTypes(userType).subscribe(data => (
      this.documentTypes = data
    ));
  }

  submit() {
    this.documentService.save(this.galleryDocument).subscribe();
  }

  cancel() {
    this.router.navigate(['/profile/gallery/documents/list/gallery']);
  }

  onUploadEnd(event: any): void {
    let args = event.args;
    this.galleryDocument.files = JSON.parse(args.response.toString()
      .replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">', '')
      .replace('<pre>', '')
      .replace('</pre>', ''));
  }

}
