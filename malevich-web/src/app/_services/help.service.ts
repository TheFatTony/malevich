import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment.dev';
import {HelpCategoryDto} from '../_transfer/helpCategoryDto';
import {HelpTopicDto} from '../_transfer/helpTopicDto';
import {HelpFilterDto} from '../_transfer/helpFilterDto';

@Injectable({
  providedIn: 'root'
})
export class HelpService {

  private url = environment.baseUrl + 'help';

  constructor(private http: HttpClient) {
  }

  getCategories() {
    return this.http
      .get<HelpCategoryDto[]>(this.url + '/categoryList ').pipe(map(data => data));
  }

  addCategory(helpCategory: HelpCategoryDto) {
    return this.http.post<HelpCategoryDto>(this.url + '/addCategory', helpCategory);
  }

  getTopics() {
    return this.http.get<HelpTopicDto[]>(this.url + '/topicList').pipe(map(data => data));
  }

  addTopic(helpTopic: HelpTopicDto) {
    return this.http.post<HelpTopicDto>(this.url + '/addTopic', helpTopic);
  }

  getTopic(id: number) {
    return this.http.get<HelpTopicDto[]>(this.url + '/topic/' + id);
  }

  filterSearch(helpFilter: HelpFilterDto) {
    return this.http.post<HelpTopicDto[]>(this.url + '/filter', helpFilter);
  }
}
