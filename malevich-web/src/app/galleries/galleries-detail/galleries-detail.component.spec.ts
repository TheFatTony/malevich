import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GalleriesDetailComponent } from './galleries-detail.component';

describe('GalleriesDetailComponent', () => {
  let component: GalleriesDetailComponent;
  let fixture: ComponentFixture<GalleriesDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GalleriesDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GalleriesDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
