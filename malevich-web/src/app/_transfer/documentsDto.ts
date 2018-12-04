import {DocumentTypeDto} from './documentTypeDto';
import {FileDto} from './fileDto';

export class DocumentsDto {
  id: number;
  file: FileDto;
  effectiveDate: Date;
  documentType: DocumentTypeDto;
}
