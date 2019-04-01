import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {KycLevel, KycLevelDto} from "../_transfer/kycLevelDto";

@Injectable({
  providedIn: 'root'
})
export class KycLevelService {

  private url = environment.baseUrl + 'kycLevels';

  constructor(private http: HttpClient) {
  }

  getAllLevels() {
    return this.http
      .get<KycLevelDto[]>(this.url + '/list');
  }

  getDetailing(level: string) {
    return this.http
      .get<Map<KycLevel, boolean>>(this.url + `/detailed/${level}`);
  }

  testLevel(testLevel: KycLevel, targetLevels: KycLevel[]) {
    return this.http
      .put<boolean>(this.url + `/testLevel/${testLevel}`, targetLevels);
  }

  // level: 'T_TIER0' | 'T_TIER1' | 'T_TIER2' | 'G_TIER0' | 'G_TIER1;
}
