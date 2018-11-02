import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AccountStateListComponent} from './account-state-list.component';

describe('AccountStateListComponent', () => {
  let component: AccountStateListComponent;
  let fixture: ComponentFixture<AccountStateListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AccountStateListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountStateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
