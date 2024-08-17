import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewRegistrationPageComponent } from './new-registration-page.component';

describe('NewRegistrationPageComponent', () => {
  let component: NewRegistrationPageComponent;
  let fixture: ComponentFixture<NewRegistrationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewRegistrationPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewRegistrationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
