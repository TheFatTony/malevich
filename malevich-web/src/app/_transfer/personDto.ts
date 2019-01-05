import {GenderDto} from "./genderDto";
import {CountryDto} from "./countryDto";
import {AddressDto} from "./addressDto";

export class PersonDto {
  id: number;
  firstName: string;
  lastName: string;
  fullName: string;
  mobile: string;
  dateOfBirth: Date;
  gender: GenderDto;
  country: CountryDto;
  addresses: AddressDto[];
}
