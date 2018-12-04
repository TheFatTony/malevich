import {DocumentTypeDto} from './documentTypeDto';
import {FileDto} from './fileDto';

export class DocumentsDto {
  id: number;
  thumbnail: FileDto;
  effectiveDate: Date;
  documentType: DocumentTypeDto;
}
