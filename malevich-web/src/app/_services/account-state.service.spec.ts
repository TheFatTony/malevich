import {inject, TestBed} from '@angular/core/testing';

import {AccountStateService} from './account-state.service';

describe('AccountStateService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccountStateService]
    });
  });

  it('should be created', inject([AccountStateService], (service: AccountStateService) => {
    expect(service).toBeTruthy();
  }));
});
