import { TestBed, async, inject } from '@angular/core/testing';

import { KycGuard } from './kyc.guard';

describe('KycGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [KycGuard]
    });
  });

  it('should ...', inject([KycGuard], (guard: KycGuard) => {
    expect(guard).toBeTruthy();
  }));
});
