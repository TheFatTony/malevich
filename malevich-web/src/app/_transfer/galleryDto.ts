import {FileDto} from "./fileDto";
import {OrganizationDto} from "./organizationDto";

export class GalleryDto {
  id: number;
  organization: OrganizationDto;
  description: string;
  thumbnail: FileDto;
  image: FileDto;
  descriptionMl: Map<string, string>;
}
