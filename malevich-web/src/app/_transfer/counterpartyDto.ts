import {CounterpartyTypeDto} from "./counterpartyTypeDto";
import {TraderDto} from "./traderDto";
import {GalleryDto} from "./galleryDto";
import {UserDto} from "./userDto";

export class CounterpartyDto {
  id: number;
  type: CounterpartyTypeDto;
  user: UserDto;
  trader: TraderDto;
  gallery: GalleryDto;
}
