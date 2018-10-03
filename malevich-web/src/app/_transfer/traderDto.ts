import {PersonDto} from "./personDto";
import {FileDto} from "./fileDto";
import {UserDto} from "./userDto";

export class TraderDto {
  person: PersonDto;
  user: UserDto;
  mobile: string;
  dateOfBirth: Date;
  gender: string;
  thumbnail: FileDto
}
