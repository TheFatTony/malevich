import { TestBed } from '@angular/core/testing';

import { KycLevelService } from './kyc-level.service';

describe('KycLevelService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: KycLevelService = TestBed.get(KycLevelService);
    expect(service).toBeTruthy();
  });
});
