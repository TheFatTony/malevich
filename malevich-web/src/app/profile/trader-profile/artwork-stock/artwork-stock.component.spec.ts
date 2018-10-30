import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtworkStockComponent } from './artwork-stock.component';

describe('ArtworkStockComponent', () => {
  let component: ArtworkStockComponent;
  let fixture: ComponentFixture<ArtworkStockComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtworkStockComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtworkStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
