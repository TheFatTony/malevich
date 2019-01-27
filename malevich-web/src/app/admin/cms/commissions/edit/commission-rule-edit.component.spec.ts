import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CommissionRuleEditComponent} from './commission-rule-edit.component';

describe('CommissionRuleEditComponent', () => {
  let component: CommissionRuleEditComponent;
  let fixture: ComponentFixture<CommissionRuleEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CommissionRuleEditComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommissionRuleEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
