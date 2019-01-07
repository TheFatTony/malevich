import { TestBed } from '@angular/core/testing';

import { CounterpartyService } from './counterparty.service';

describe('CounterpartyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CounterpartyService = TestBed.get(CounterpartyService);
    expect(service).toBeTruthy();
  });
});
