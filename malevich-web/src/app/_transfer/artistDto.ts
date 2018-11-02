import {FileDto} from "./fileDto";
import {PersonDto} from "./personDto";

export class ArtistDto {
  id: number;
  person: PersonDto;
  thumbnail: FileDto;
  image: FileDto;
  fullNameMl: Map<string, string>;
  descriptionMl: Map<string, string>;
}
