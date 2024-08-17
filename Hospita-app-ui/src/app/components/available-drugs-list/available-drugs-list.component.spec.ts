import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableDrugsListComponent } from './available-drugs-list.component';

describe('AvailableDrugsListComponent', () => {
  let component: AvailableDrugsListComponent;
  let fixture: ComponentFixture<AvailableDrugsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AvailableDrugsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AvailableDrugsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
