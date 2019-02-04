import { TestBed } from '@angular/core/testing';

import { PaymentMethodBitcoinService } from './payment-method-bitcoin.service';

describe('PaymentMethodBitcoinService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PaymentMethodBitcoinService = TestBed.get(PaymentMethodBitcoinService);
    expect(service).toBeTruthy();
  });
});
