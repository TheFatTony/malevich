import {inject, TestBed} from '@angular/core/testing';

import {TraderService} from './trader.service';

describe('TraderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TraderService]
    });
  });

  it('should be created', inject([TraderService], (service: TraderService) => {
    expect(service).toBeTruthy();
  }));
});
