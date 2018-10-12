import {PersonDto} from "./personDto";
import {FileDto} from "./fileDto";
import {UserDto} from "./userDto";
import {GenderDto} from "./genderDto";
import {CountryDto} from "./countryDto";
import {AddressDto} from "./addressDto";

export class TraderDto {
  id: number;
  person: PersonDto;
  title: string;
  user: UserDto;
  mobile: string;
  dateOfBirth: Date;
  gender: GenderDto;
  country: CountryDto;
  address: AddressDto;
  thumbnail: FileDto;
}
