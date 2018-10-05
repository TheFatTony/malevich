import {PersonDto} from "./personDto";
import {FileDto} from "./fileDto";
import {UserDto} from "./userDto";
import {GenderDto} from "./genderDto";

export class TraderDto {
  person: PersonDto;
  user: UserDto;
  mobile: string;
  dateOfBirth: Date;
  gender: GenderDto;
  thumbnail: FileDto
}
