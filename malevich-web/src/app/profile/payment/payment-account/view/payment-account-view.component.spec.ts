import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentAccountViewComponent } from './payment-account-view.component';

describe('PaymentAccountViewComponent', () => {
  let component: PaymentAccountViewComponent;
  let fixture: ComponentFixture<PaymentAccountViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentAccountViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentAccountViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
