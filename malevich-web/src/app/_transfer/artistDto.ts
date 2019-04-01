import {FileDto} from "../../../node_modules/yinyang-core";

export class ArtistDto {
  id: number;
  thumbnail: FileDto;
  image: FileDto;
  fullNameMl: Map<string, string>;
  descriptionMl: Map<string, string>;
  dateOfBirth: Date;
  dateOfDeath: Date;
}
