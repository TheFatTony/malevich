import {CounterpartyTypeDto} from "./counterpartyTypeDto";
import {TraderDto} from "./traderDto";
import {GalleryDto} from "./galleryDto";
import {UserDto} from "./userDto";
import {FileDto} from "./fileDto";

export class CounterpartyDto {
  id: number;
  type: CounterpartyTypeDto;
  user: UserDto;
  trader: TraderDto;
  gallery: GalleryDto;
  image: FileDto;
}
