import {ArtworkStockDto} from "./artworkStockDto";
import {OrderDto} from "./orderDto";

export class TradeHistoryDto {
  id: number;
  artworkStock: ArtworkStockDto;
  effectiveDate: Date;
  askOrder: OrderDto;
  bidOrder: OrderDto;
  amount: number;
  quantity: number;
}
