import {DocumentTypeDto} from './documentTypeDto';
import {FileDto} from '../../../node_modules/yinyang-core';

export class DocumentsDto {
  id: number;
  files: FileDto;
  effectiveDate: Date;
  documentType: DocumentTypeDto;
}
