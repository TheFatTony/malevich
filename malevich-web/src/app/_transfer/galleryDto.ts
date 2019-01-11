import {OrganizationDto} from "./organizationDto";
import {ParticipantDto} from "./participantDto";
import {FileDto} from "../../../node_modules/yinyang-core";

export class GalleryDto extends ParticipantDto{
  organization: OrganizationDto;
  thumbnail: FileDto;
  image: FileDto;
  descriptionMl: Map<string, string>;
}
