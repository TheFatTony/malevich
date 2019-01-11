import {CategoryDto} from "./categoryDto";
import {ArtistDto} from "./artistDto";
import {FileDto} from "../../../node_modules/yinyang-core";

export class ArtworkDto {
  id: number;
  estimatedPrice: number;
  category: CategoryDto;
  artist: ArtistDto;
  thumbnail: FileDto;
  image: FileDto;
  titleMl: Map<string, string>;
  descriptionMl: Map<string, string>;
}
