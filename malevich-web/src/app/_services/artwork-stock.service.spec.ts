import { TestBed, inject } from '@angular/core/testing';

import { ArtworkStockService } from './artwork-stock.service';

describe('ArtworkStockService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ArtworkStockService]
    });
  });

  it('should be created', inject([ArtworkStockService], (service: ArtworkStockService) => {
    expect(service).toBeTruthy();
  }));
});
