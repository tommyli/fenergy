import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginNmiListComponent } from './login-nmi-list.component';

describe('LoginNmiListComponent', () => {
  let component: LoginNmiListComponent;
  let fixture: ComponentFixture<LoginNmiListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginNmiListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginNmiListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
