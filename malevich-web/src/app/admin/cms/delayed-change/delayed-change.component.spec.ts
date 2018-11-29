import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DelayedChangeComponent } from './delayed-change.component';

describe('DelayedChangeComponent', () => {
  let component: DelayedChangeComponent;
  let fixture: ComponentFixture<DelayedChangeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DelayedChangeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DelayedChangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
