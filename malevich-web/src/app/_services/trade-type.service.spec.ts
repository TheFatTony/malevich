import {inject, TestBed} from '@angular/core/testing';

import {TradeTypeService} from './trade-type.service';

describe('TradeTypeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TradeTypeService]
    });
  });

  it('should be created', inject([TradeTypeService], (service: TradeTypeService) => {
    expect(service).toBeTruthy();
  }));
});
