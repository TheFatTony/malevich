import {DocumentTypeDto} from './documentTypeDto';
import {FileDto} from './fileDto';

export class DocumentsDto {
  id: number;
  files: FileDto;
  effectiveDate: Date;
  documentType: DocumentTypeDto;
}
