import {CounterpartyTypeDto} from "./counterpartyTypeDto";
import {ParticipantDto} from "./participantDto";

export class CounterpartyDto {
  id: number;
  type: CounterpartyTypeDto;
  participant : ParticipantDto;
}
