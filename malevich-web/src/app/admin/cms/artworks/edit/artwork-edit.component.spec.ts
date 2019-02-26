import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtworkEditComponent } from './artwork-edit.component';

describe('ArtworkEditComponent', () => {
  let component: ArtworkEditComponent;
  let fixture: ComponentFixture<ArtworkEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtworkEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtworkEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
