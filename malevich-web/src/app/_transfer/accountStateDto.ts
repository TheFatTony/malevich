import {ArtworkStockDto} from "./artworkStockDto";
import {CounterpartyDto} from "./counterpartyDto";

export class AccountStateDto {
  id: number;
  party: CounterpartyDto;
  artworkStock: ArtworkStockDto;
  amount: number;
  quantity: number;
}
