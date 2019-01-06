import {PersonDto} from "./personDto";
import {ParticipantDto} from "./participantDto";

export class TraderDto extends ParticipantDto{
  person: PersonDto;
}
