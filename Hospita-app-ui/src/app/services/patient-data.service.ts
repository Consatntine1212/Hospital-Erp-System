import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../model/user.model';
import { Register_form } from '../model/Register_form.model';
@Injectable({
  providedIn: 'root'
})
export class PatientDataService {
  private patientSource = new BehaviorSubject<Register_form | null>(null);
  currentPatient = this.patientSource.asObservable();

  changePatient(patient: Register_form) {
    this.patientSource.next(patient);
  }
}
