import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ArtworksListComponent} from './artworks-list.component';

describe('ArtworksListComponent', () => {
  let component: ArtworksListComponent;
  let fixture: ComponentFixture<ArtworksListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ArtworksListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtworksListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
