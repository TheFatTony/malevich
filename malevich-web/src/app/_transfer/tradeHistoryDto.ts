import {ArtworkStockDto} from "./artworkStockDto";
import {OrderDto} from "./orderDto";

export class TradeHistoryDto {
  id: number;
  artworkStock: ArtworkStockDto;
  effectiveDate: string;
  askOrder: OrderDto;
  bidOrder: OrderDto;
  amount: number;
  quantity: number;
}
