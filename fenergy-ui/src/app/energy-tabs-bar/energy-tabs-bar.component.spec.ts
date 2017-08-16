import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnergyTabsBarComponent } from './energy-tabs-bar.component';

describe('EnergyTabsBarComponent', () => {
  let component: EnergyTabsBarComponent;
  let fixture: ComponentFixture<EnergyTabsBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnergyTabsBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnergyTabsBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
