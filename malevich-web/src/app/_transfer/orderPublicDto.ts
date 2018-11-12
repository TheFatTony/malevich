import {OrderTypeDto} from "./orderTypeDto";
import {ArtworkStockDto} from "./artworkStockDto";

export class OrderPublicDto {
  id: number;
  type: OrderTypeDto;
  artworkStock: ArtworkStockDto;
  amount: number;
  isOwn: boolean;
}
