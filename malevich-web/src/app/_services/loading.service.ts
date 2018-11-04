import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  public loading: boolean = false;

  constructor() {
  }

  public hide(): void {
    this.loading = false;
  }

  public show(): void {
    this.loading = true;
  }

  public isLoading(): boolean {
    return this.loading;
  }

}
