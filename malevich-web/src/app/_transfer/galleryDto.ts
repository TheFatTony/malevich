import {OrganizationDto} from "./organizationDto";
import {AddressDto} from "./addressDto";

export class GalleryDto {
  id: number;
  organization: OrganizationDto;
  descriptionMl: Map<string, string>;
  addresses: AddressDto[];
}
