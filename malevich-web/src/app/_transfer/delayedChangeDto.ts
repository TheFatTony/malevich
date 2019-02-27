import {UserDto} from "yinyang-core";

export class DelayedChangeDto {
  id: number;
  typeId: string;
  user: UserDto;
  payload: string;
  comment: string;
}
