import {GenderDto} from "./genderDto";

export class PersonDto {
  id: number;
  firstName: string;
  lastName: string;
  fullName: string;
  dateOfBirth: Date;
  gender: GenderDto;
}
