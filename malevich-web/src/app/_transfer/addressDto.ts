import {CountryDto} from "./countryDto";
import {TraderDto} from "./traderDto";

export class AddressDto {
  id: number;
  trader: TraderDto;
  street: string;
  postalCode: string;
  state: string;
  city: string;
  country: CountryDto;
}
