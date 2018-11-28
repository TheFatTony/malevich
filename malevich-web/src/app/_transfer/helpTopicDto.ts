import {HelpCategoryDto} from './helpCategoryDto';

export class HelpTopicDto {
  id: number;
  topicNameMl: Map<string, string>;
  bodyMl: Map<string, string>;
  helpCategory: HelpCategoryDto;
}
