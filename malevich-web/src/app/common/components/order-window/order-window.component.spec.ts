import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderWindowComponent } from './order-window.component';

describe('OrderWindowComponent', () => {
  let component: OrderWindowComponent;
  let fixture: ComponentFixture<OrderWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
