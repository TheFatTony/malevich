import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentBitcoinComponent } from './payment-bitcoin.component';

describe('PaymentBitcoinComponent', () => {
  let component: PaymentBitcoinComponent;
  let fixture: ComponentFixture<PaymentBitcoinComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentBitcoinComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentBitcoinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
