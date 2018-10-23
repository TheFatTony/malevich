import {CounterpartyTypeDto} from "./counterpartyTypeDto";
import {TraderDto} from "./traderDto";
import {GalleryDto} from "./galleryDto";

export class CounterpartyDto {
  id: number;
  type: CounterpartyTypeDto;
  trader: TraderDto;
  gallery: GalleryDto;
}
