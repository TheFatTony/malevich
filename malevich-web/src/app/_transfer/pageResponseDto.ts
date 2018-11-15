import List = Mocha.reporters.List;
import {ArtworkStockDto} from './artworkStockDto';

export class PageResponseDto {
  totalElements: number;
  totalPages: number;
  stockSto: List<ArtworkStockDto>;
}
