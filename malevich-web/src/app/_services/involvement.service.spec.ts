import {inject, TestBed} from '@angular/core/testing';

import {InvolvementService} from './involvement.service';

describe('InvolvementService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [InvolvementService]
    });
  });

  it('should be created', inject([InvolvementService], (service: InvolvementService) => {
    expect(service).toBeTruthy();
  }));
});
