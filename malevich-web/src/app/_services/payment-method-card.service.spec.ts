import { TestBed } from '@angular/core/testing';

import { PaymentMethodCardService } from './payment-method-card.service';

describe('PaymentMethodCardService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PaymentMethodCardService = TestBed.get(PaymentMethodCardService);
    expect(service).toBeTruthy();
  });
});
