import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrescriptionSearchComponent } from './prescription-search.component';

describe('PrescriptionSearchComponent', () => {
  let component: PrescriptionSearchComponent;
  let fixture: ComponentFixture<PrescriptionSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrescriptionSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrescriptionSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
