import {OrderTypeDto} from "./orderTypeDto";
import {ArtworkStockDto} from "./artworkStockDto";
import {CounterpartyDto} from "./counterpartyDto";
import {TradeTypeDto} from "./tradeTypeDto";
import {TransactionTypeDto} from "./transactionTypeDto";

export class TransactionsDto {
  id: number;
  type: TransactionTypeDto;
  effectiveDate: Date;
  party: CounterpartyDto;
  counterparty: CounterpartyDto;
  artworkStock: ArtworkStockDto;
  amount: number;
  quantity: number;
}
