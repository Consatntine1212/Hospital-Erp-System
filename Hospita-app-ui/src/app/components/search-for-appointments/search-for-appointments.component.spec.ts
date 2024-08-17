import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchForAppointmentsComponent } from './search-for-appointments.component';

describe('SearchForAppointmentsComponent', () => {
  let component: SearchForAppointmentsComponent;
  let fixture: ComponentFixture<SearchForAppointmentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchForAppointmentsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchForAppointmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
