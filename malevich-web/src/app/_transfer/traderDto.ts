import {PersonDto} from "./personDto";
import {FileDto} from "./fileDto";
import {UserDto} from "./userDto";
import {GenderDto} from "./genderDto";
import {CountryDto} from "./countryDto";

export class TraderDto {
  person: PersonDto;
  user: UserDto;
  mobile: string;
  dateOfBirth: Date;
  gender: GenderDto;
  country: CountryDto;
  thumbnail: FileDto;
}
