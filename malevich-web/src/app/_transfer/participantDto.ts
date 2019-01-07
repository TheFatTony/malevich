import {FileDto} from "./fileDto";
import {UserDto} from "./userDto";
import {CountryDto} from "./countryDto";
import {AddressDto} from "./addressDto";
import {ParticipantTypeDto} from "./participantTypeDto";

export class ParticipantDto {
  id: number;
  type: ParticipantTypeDto;
  users: UserDto[];
  phoneNumber: string;
  country: CountryDto;
  thumbnail: FileDto;
  addresses: AddressDto[];
}
