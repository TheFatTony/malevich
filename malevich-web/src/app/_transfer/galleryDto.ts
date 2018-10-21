import {FileDto} from "./fileDto";
import {OrganizationDto} from "./organizationDto";
import {AddressDto} from "./addressDto";

export class GalleryDto {
  id: number;
  organization: OrganizationDto;
  description: string;
  thumbnail: FileDto;
  image: FileDto;
  descriptionMl: Map<string, string>;
  addresses: AddressDto[];
}
