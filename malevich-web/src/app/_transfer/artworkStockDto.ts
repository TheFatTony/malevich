import {GalleryDto} from "./galleryDto";
import {ArtworkDto} from "./artworkDto";

export class ArtworkStockDto {
  id: number;
  artwork: ArtworkDto;
  gallery: GalleryDto;
  instantPrice: number;
  bestBid: number;
  lastPrice: number;
}
