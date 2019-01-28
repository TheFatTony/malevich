import { TestBed } from '@angular/core/testing';

import { CommissionRuleService } from './commission-rule.service';

describe('CommissionRuleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CommissionRuleService = TestBed.get(CommissionRuleService);
    expect(service).toBeTruthy();
  });
});
