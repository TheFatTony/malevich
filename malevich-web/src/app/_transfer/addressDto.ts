import {CountryDto} from "./countryDto";

export class AddressDto {
  id: number;
  street: string;
  postalCode: string;
  state: string;
  city: string;
  country: CountryDto;
}
