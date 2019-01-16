import {OrderTypeDto} from "./orderTypeDto";
import {ArtworkStockDto} from "./artworkStockDto";
import {TradeTypeDto} from "./tradeTypeDto";
import {ParticipantDto} from "./participantDto";

export class OrderDto {
  id: number;
  type: OrderTypeDto;
  artworkStock: ArtworkStockDto;
  party: ParticipantDto;
  tradeType: TradeTypeDto;
  amount: number;
  effectiveDate: Date;
  expirationDate: Date;
  bestBid: number;
  currentAsk: number;
}
