import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ArtistsDetailComponent} from './artists-detail.component';

describe('ArtistsDetailComponent', () => {
  let component: ArtistsDetailComponent;
  let fixture: ComponentFixture<ArtistsDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ArtistsDetailComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtistsDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
