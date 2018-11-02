import {inject, TestBed} from '@angular/core/testing';

import {TradeHistoryService} from './trade-history.service';

describe('TradeHistoryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TradeHistoryService]
    });
  });

  it('should be created', inject([TradeHistoryService], (service: TradeHistoryService) => {
    expect(service).toBeTruthy();
  }));
});
