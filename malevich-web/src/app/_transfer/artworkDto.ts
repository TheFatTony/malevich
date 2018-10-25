import {CategoryDto} from "./categoryDto";
import {FileDto} from "./fileDto";
import {ArtistDto} from "./artistDto";

export class ArtworkDto {
  id: number;
  title: string;
  description: string;
  estimatedPrice: number;
  category: CategoryDto;
  artist: ArtistDto;
  thumbnail: FileDto;
  image: FileDto;
  titleMl: Map<string, string>;
  descriptionMl: Map<string, string>;
}
