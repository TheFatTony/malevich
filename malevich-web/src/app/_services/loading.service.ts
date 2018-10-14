import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  public loading: boolean = false;

  constructor() {
  }

  public hide(): void {
    console.info("this.loading = false;");
    this.loading = false;
  }

  public show(): void {
    console.info("this.loading = true;");
    this.loading = true;
  }

  public isLoading(): boolean {
    return this.loading;
  }

}
