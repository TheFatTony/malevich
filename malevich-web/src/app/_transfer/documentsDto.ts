import {DocumentTypeDto} from './documentTypeDto';

export class DocumentsDto {
  id: number;
  fileName: string;
  effectiveDate: Date;
  documentType: DocumentTypeDto;
}
