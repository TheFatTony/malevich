import { TestBed } from '@angular/core/testing';

import { DelayedChangeService } from './delayed-change.service';

describe('DelayedChangeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DelayedChangeService = TestBed.get(DelayedChangeService);
    expect(service).toBeTruthy();
  });
});
