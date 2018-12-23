import {CounterpartyTypeDto} from "./counterpartyTypeDto";
import {GalleryDto} from "./galleryDto";
import {UserDto} from "./userDto";
import {FileDto} from "./fileDto";
import {PersonDto} from "./personDto";
import {OrganizationDto} from "./organizationDto";

export class CounterpartyDto {
  id: number;
  type: CounterpartyTypeDto;
  user: UserDto;
  person: PersonDto;
  organization: OrganizationDto;
  gallery: GalleryDto;
  image: FileDto;
}
