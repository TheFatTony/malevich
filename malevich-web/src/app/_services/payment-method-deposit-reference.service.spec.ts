import { TestBed } from '@angular/core/testing';

import { PaymentMethodDepositReferenceService } from './payment-method-deposit-reference.service';

describe('PaymentMethodDepositReferenceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PaymentMethodDepositReferenceService = TestBed.get(PaymentMethodDepositReferenceService);
    expect(service).toBeTruthy();
  });
});
