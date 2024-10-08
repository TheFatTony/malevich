import {CountryDto} from "./countryDto";
import {AddressDto} from "./addressDto";
import {ParticipantTypeDto} from "./participantTypeDto";
import {PersonDto} from "./personDto";
import {OrganizationDto} from "./organizationDto";
import {UserDto, FileDto} from "../../../node_modules/yinyang-core";

export class ParticipantDto {
  id: number;
  type: ParticipantTypeDto;
  users: UserDto[];
  phoneNumber: string;
  country: CountryDto;
  thumbnail: FileDto;
  addresses: AddressDto[];

  organization: OrganizationDto;
  image: FileDto;
  descriptionMl: Map<string, string>;

  person: PersonDto;
}
