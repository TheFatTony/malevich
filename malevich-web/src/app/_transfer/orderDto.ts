import {OrderTypeDto} from "./orderTypeDto";
import {ArtworkStockDto} from "./artworkStockDto";
import {CounterpartyDto} from "./counterpartyDto";
import {TradeTypeDto} from "./tradeTypeDto";

export class OrderDto {
  id: number;
  type: OrderTypeDto;
  artworkStock: ArtworkStockDto;
  party: CounterpartyDto;
  tradeType: TradeTypeDto;
  amount: number;
  effectiveDate: Date;
}
