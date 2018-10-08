import {FileDto} from "./fileDto";
import {PersonDto} from "./personDto";

export class ArtistDto {
  id: number;
  person: PersonDto;
  description: string;
  thumbnail: FileDto;
  image: FileDto;
  descriptionMl: Map<string, string>;
}
