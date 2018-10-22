import {GalleryDto} from "./galleryDto";
import {ArtworkDto} from "./artworkDto";

export class ArtworkStockDto {
  id: number;
  artwork: ArtworkDto;
  gallery: GalleryDto;
  price: number;
}
