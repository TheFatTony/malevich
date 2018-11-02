import {inject, TestBed} from '@angular/core/testing';

import {PaymentMethodTypeService} from './payment-method-type.service';

describe('PaymentMethodTypeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PaymentMethodTypeService]
    });
  });

  it('should be created', inject([PaymentMethodTypeService], (service: PaymentMethodTypeService) => {
    expect(service).toBeTruthy();
  }));
});
