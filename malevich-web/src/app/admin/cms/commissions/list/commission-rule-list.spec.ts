import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CommissionRuleListComponent} from './commission-rule-list.component';

describe('CommissionRuleListComponent', () => {
  let component: CommissionRuleListComponent;
  let fixture: ComponentFixture<CommissionRuleListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CommissionRuleListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommissionRuleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
