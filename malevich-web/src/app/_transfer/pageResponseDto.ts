import {PageParamsDto} from "./pageParamsDto";

export class PageResponseDto<T> extends PageParamsDto{
  totalPages: number;
  sortBy: string;
  data: T[];
}

