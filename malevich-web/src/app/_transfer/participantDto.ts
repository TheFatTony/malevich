import {FileDto} from "./fileDto";
import {UserDto} from "./userDto";
import {CountryDto} from "./countryDto";
import {AddressDto} from "./addressDto";

export class ParticipantDto {
  id: number;
  users: UserDto[];
  phoneNumber: string;
  country: CountryDto;
  thumbnail: FileDto;
  addresses: AddressDto[];
}
