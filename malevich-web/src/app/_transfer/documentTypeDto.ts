import {ParticipantTypeDto} from "./participantTypeDto";

export class DocumentTypeDto {
  id: string;
  nameMl: Map<string, string>;
  participantTypes: ParticipantTypeDto[];
}
