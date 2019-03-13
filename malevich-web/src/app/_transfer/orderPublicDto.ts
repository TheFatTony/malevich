import {OrderTypeDto} from "./orderTypeDto";
import {ArtworkStockDto} from "./artworkStockDto";

export class OrderPublicDto {
  id: string;
  type: OrderTypeDto;
  artworkStock: ArtworkStockDto;
  amount: number;
  isOwn: boolean;
}
