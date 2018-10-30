import {ArtworkStockDto} from "./artworkStockDto";
import {CounterpartyDto} from "./counterpartyDto";
import {TransactionTypeDto} from "./transactionTypeDto";
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
