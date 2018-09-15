import {AfterViewInit, Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit{
  title = 'malevich-web';

  ngAfterViewInit(): void {
    $['HSCore'].components.HSGoTo.init('.js-go-to');
  }
}
