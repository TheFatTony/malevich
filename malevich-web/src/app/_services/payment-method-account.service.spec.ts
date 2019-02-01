import { TestBed } from '@angular/core/testing';

import { PaymentMethodAccountService } from './payment-method-account.service';

describe('PaymentMethodAccountService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PaymentMethodAccountService = TestBed.get(PaymentMethodAccountService);
    expect(service).toBeTruthy();
  });
});
