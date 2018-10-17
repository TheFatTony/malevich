import {PersonDto} from "./personDto";
import {FileDto} from "./fileDto";
import {UserDto} from "./userDto";
import {GenderDto} from "./genderDto";
import {CountryDto} from "./countryDto";
import {AddressDto} from "./addressDto";

export class TraderDto {
  id: number;
  person: PersonDto;
  user: UserDto;
  mobile: string;
  dateOfBirth: Date;
  gender: GenderDto;
  country: CountryDto;
  thumbnail: FileDto;
  addresses: AddressDto[];
}
