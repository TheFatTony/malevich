import {FileDto} from "./fileDto";

export class GalleryDto {
  id: number;
  thumbnail: FileDto;
  image: FileDto;
  titleMl: Map<string, string>;
  descriptionMl: Map<string, string>;
}
