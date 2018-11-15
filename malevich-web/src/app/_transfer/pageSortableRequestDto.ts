import {PageResponseDto} from './pageResponseDto';

export class PageSortableRequestDto extends PageResponseDto {
  sort: number;
  page: number;
  size: number;
}
