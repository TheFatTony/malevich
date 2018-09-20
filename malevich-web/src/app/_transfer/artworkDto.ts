import {CategoryDto} from "./categoryDto";
import {FileDto} from "./fileDto";

export class ArtworkDto {
  id: number;
  title: string;
  description: string;
  price: number;
  category: CategoryDto;
  thumbnail: FileDto;
  titleMl: Map<string, string>;
  descriptionMl: Map<string, string>;
}
