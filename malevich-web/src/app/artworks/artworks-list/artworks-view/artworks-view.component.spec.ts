import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtworksViewComponent } from './artworks-view.component';

describe('ArtworksViewComponent', () => {
  let component: ArtworksViewComponent;
  let fixture: ComponentFixture<ArtworksViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtworksViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtworksViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
