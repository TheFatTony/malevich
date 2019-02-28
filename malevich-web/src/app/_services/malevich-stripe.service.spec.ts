import { TestBed } from '@angular/core/testing';

import { MalevichStripeService } from './malevich-stripe.service';

describe('MalevichStripeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MalevichStripeService = TestBed.get(MalevichStripeService);
    expect(service).toBeTruthy();
  });
});
