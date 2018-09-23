import {FileDto} from "./fileDto";
import {PersonDto} from "./personDto";

export class ArtistDto {
  id: number;
  person: PersonDto;
  description: string;
  thumbnail: FileDto;
  descriptionMl: Map<string, string>;
}
