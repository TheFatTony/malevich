import {OrderTypeDto} from "./orderTypeDto";
import {ArtworkStockDto} from "./artworkStockDto";
import {CounterpartyDto} from "./counterpartyDto";
import {TradeTypeDto} from "./tradeTypeDto";
import {TransactionTypeDto} from "./transactionTypeDto";

export class AccountStateDto {
  id: number;
  party: CounterpartyDto;
  artworkStock: ArtworkStockDto;
  amount: number;
  quantity: number;
}
