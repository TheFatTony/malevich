import {FileDto} from "./fileDto";
import {OrganizationDto} from "./organizationDto";
import {ParticipantDto} from "./participantDto";

export class GalleryDto extends ParticipantDto{
  organization: OrganizationDto;
  thumbnail: FileDto;
  image: FileDto;
  descriptionMl: Map<string, string>;
}
