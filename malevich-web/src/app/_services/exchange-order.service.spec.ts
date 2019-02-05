import { TestBed } from '@angular/core/testing';

import { ExchangeOrderService } from './exchange-order.service';

describe('ExchangeOrderService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ExchangeOrderService = TestBed.get(ExchangeOrderService);
    expect(service).toBeTruthy();
  });
});
