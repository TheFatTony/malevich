import {ArtworkStockDto} from "./artworkStockDto";
import {ParticipantDto} from "./participantDto";

export class AccountStateDto {
  id: number;
  party: ParticipantDto;
  artworkStock: ArtworkStockDto;
  amount: number;
  quantity: number;
}
