import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentAccountEditComponent } from './payment-account-edit.component';

describe('PaymentAccountEditComponent', () => {
  let component: PaymentAccountEditComponent;
  let fixture: ComponentFixture<PaymentAccountEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentAccountEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentAccountEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
